using System.Collections.Generic;
using System.IO;
using System.Text;
using UnityEngine;
using UnityEngine.Tilemaps;

#if UNITY_EDITOR
using UnityEditor;
#endif

/// <summary>
/// Genera un mapa de masmorra procedural i l'exporta a un fitxer .txt
/// compatible amb el format NetHack (com level01.txt).
///
/// CONFIGURACIÓ:
///   1. Afegeix aquest script a un GameObject buit de l'escena.
///   2. Assigna el Tilemap al camp "tilemap" a l'Inspector.
///   3. Assigna els TileBase corresponents a cada símbol (paret, terra, etc.)
///      — aquests tiles han de venir del teu tilemap absurd128.png tallat a 32x32.
///   4. Prem "Generate & Export Map" a l'Inspector (botó de l'Editor).
///   5. El fitxer .txt es desa a Assets/GeneratedMaps/
/// </summary>
public class DungeonMapGenerator : MonoBehaviour
{
    [Header("Tilemap Target")]
    public Tilemap tilemap;

    [Header("Tiles del Tilemap (absurd128.png, 32x32 per tile)")]
    public TileBase tileWall;    // '#'  paret
    public TileBase tileFloor;   // '.'  terra
    public TileBase tileDoor;    // '+'  porta
    public TileBase tilePlayer;  // '@'  jugador
    public TileBase tileFood;    // '%'  menjar
    public TileBase tilePotion;  // '!'  poció
    public TileBase tileGold;    // '$'  tresor
    public TileBase tileStairs;  // '>'  escales avall
    public TileBase tileWeapon;  // ')'  arma

    [Header("Mides del Mapa")]
    [Range(20, 100)] public int mapWidth = 60;
    [Range(10, 50)] public int mapHeight = 20;

    [Header("Generació")]
    [Range(2, 15)] public int numRooms = 6;
    public int seed = 0;              // 0 = aleatori cada cop
    public string outputFileName = "level_generated";

    // Mapa intern: cada cel·la és un char NetHack
    private char[,] map;

    // -----------------------------------------------------------------------
    // SÍMBOLS NetHack
    // -----------------------------------------------------------------------
    const char EMPTY = ' ';
    const char WALL = '#';
    const char FLOOR = '.';
    const char DOOR = '+';
    const char PLAYER = '@';
    const char FOOD = '%';
    const char POTION = '!';
    const char GOLD = '$';
    const char STAIRS = '>';
    const char WEAPON = ')';

    // -----------------------------------------------------------------------
    // PUNT D'ENTRADA — cridat des del botó de l'Editor
    // -----------------------------------------------------------------------
    public void GenerateAndExport()
    {
        if (seed != 0) Random.InitState(seed);
        else Random.InitState(System.DateTime.Now.Millisecond);

        map = new char[mapHeight, mapWidth];
        InitMap();

        List<RectInt> rooms = PlaceRooms();
        ConnectRooms(rooms);
        DrawWalls();
        AddDoors(rooms);
        PlaceItems(rooms);
        PlacePlayer(rooms);

        if (tilemap != null) PaintTilemap();
        ExportToTxt();
    }

    // -----------------------------------------------------------------------
    // 1. INICIALITZAR tot a EMPTY
    // -----------------------------------------------------------------------
    void InitMap()
    {
        for (int r = 0; r < mapHeight; r++)
            for (int c = 0; c < mapWidth; c++)
                map[r, c] = EMPTY;
    }

    // -----------------------------------------------------------------------
    // 2. COL·LOCAR HABITACIONS rectangulars sense solapament
    // -----------------------------------------------------------------------
    List<RectInt> PlaceRooms()
    {
        var rooms = new List<RectInt>();
        int maxTries = numRooms * 20;

        for (int i = 0; i < maxTries && rooms.Count < numRooms; i++)
        {
            int w = Random.Range(5, 13);
            int h = Random.Range(3, 8);
            int rx = Random.Range(1, mapWidth - w - 1);
            int ry = Random.Range(1, mapHeight - h - 1);

            var candidate = new RectInt(rx, ry, w, h);
            bool overlap = false;

            foreach (var r in rooms)
            {
                // Marge d'1 cel·la entre habitacions
                if (candidate.xMin < r.xMax + 1 && candidate.xMax > r.xMin - 1 &&
                    candidate.yMin < r.yMax + 1 && candidate.yMax > r.yMin - 1)
                {
                    overlap = true;
                    break;
                }
            }

            if (!overlap)
            {
                CarveRoom(candidate);
                rooms.Add(candidate);
            }
        }
        return rooms;
    }

    void CarveRoom(RectInt room)
    {
        for (int r = room.yMin; r < room.yMax; r++)
            for (int c = room.xMin; c < room.xMax; c++)
                map[r, c] = FLOOR;
    }

    // -----------------------------------------------------------------------
    // 3. CONNECTAR habitacions amb corredors en L
    // -----------------------------------------------------------------------
    void ConnectRooms(List<RectInt> rooms)
    {
        for (int i = 1; i < rooms.Count; i++)
        {
            Vector2Int a = RoomCenter(rooms[i - 1]);
            Vector2Int b = RoomCenter(rooms[i]);
            CarveCorridor(a, b);
        }
    }

    Vector2Int RoomCenter(RectInt r) =>
        new Vector2Int(r.xMin + r.width / 2, r.yMin + r.height / 2);

    void CarveCorridor(Vector2Int a, Vector2Int b)
    {
        // Primer horitzontal, després vertical (o a l'inrevés aleatòriament)
        if (Random.value < 0.5f)
        {
            CarveH(a.y, a.x, b.x);
            CarveV(b.x, a.y, b.y);
        }
        else
        {
            CarveV(a.x, a.y, b.y);
            CarveH(b.y, a.x, b.x);
        }
    }

    void CarveH(int row, int x1, int x2)
    {
        int minX = Mathf.Min(x1, x2), maxX = Mathf.Max(x1, x2);
        for (int c = minX; c <= maxX; c++)
            if (InBounds(row, c)) map[row, c] = FLOOR;
    }

    void CarveV(int col, int y1, int y2)
    {
        int minY = Mathf.Min(y1, y2), maxY = Mathf.Max(y1, y2);
        for (int r = minY; r <= maxY; r++)
            if (InBounds(r, col)) map[r, col] = FLOOR;
    }

    // -----------------------------------------------------------------------
    // 4. DIBUIXAR PARETS al voltant del terra
    // -----------------------------------------------------------------------
    void DrawWalls()
    {
        for (int r = 0; r < mapHeight; r++)
            for (int c = 0; c < mapWidth; c++)
                if (map[r, c] == FLOOR)
                    for (int dr = -1; dr <= 1; dr++)
                        for (int dc = -1; dc <= 1; dc++)
                            if (InBounds(r + dr, c + dc) && map[r + dr, c + dc] == EMPTY)
                                map[r + dr, c + dc] = WALL;
    }

    // -----------------------------------------------------------------------
    // 5. AFEGIR PORTES als llindars entre corredor i habitació
    // -----------------------------------------------------------------------
    void AddDoors(List<RectInt> rooms)
    {
        for (int r = 1; r < mapHeight - 1; r++)
            for (int c = 1; c < mapWidth - 1; c++)
                if (map[r, c] == FLOOR && Random.value < 0.3f)
                {
                    bool h = map[r, c - 1] == WALL && map[r, c + 1] == WALL
                          && map[r - 1, c] == FLOOR && map[r + 1, c] == FLOOR;
                    bool v = map[r - 1, c] == WALL && map[r + 1, c] == WALL
                          && map[r, c - 1] == FLOOR && map[r, c + 1] == FLOOR;
                    if (h || v) map[r, c] = DOOR;
                }
    }

    // -----------------------------------------------------------------------
    // 6. COL·LOCAR OBJECTES a les habitacions
    // -----------------------------------------------------------------------
    void PlaceItems(List<RectInt> rooms)
    {
        char[] items = { FOOD, POTION, GOLD, WEAPON };

        foreach (var room in rooms)
        {
            int count = Random.Range(0, 4);
            for (int i = 0; i < count; i++)
            {
                Vector2Int pos = RandomFloorInRoom(room);
                if (pos.x >= 0)
                    map[pos.y, pos.x] = items[Random.Range(0, items.Length)];
            }
        }
    }

    // -----------------------------------------------------------------------
    // 7. COL·LOCAR JUGADOR i ESCALES
    // -----------------------------------------------------------------------
    void PlacePlayer(List<RectInt> rooms)
    {
        if (rooms.Count == 0) return;

        // Jugador a la primera habitació
        Vector2Int sp = RandomFloorInRoom(rooms[0]);
        if (sp.x >= 0) map[sp.y, sp.x] = PLAYER;

        // Escales a l'última habitació
        Vector2Int ep = RandomFloorInRoom(rooms[rooms.Count - 1]);
        if (ep.x >= 0) map[ep.y, ep.x] = STAIRS;
    }

    Vector2Int RandomFloorInRoom(RectInt room, int tries = 20)
    {
        for (int i = 0; i < tries; i++)
        {
            int c = Random.Range(room.xMin + 1, room.xMax - 1);
            int r = Random.Range(room.yMin + 1, room.yMax - 1);
            if (InBounds(r, c) && map[r, c] == FLOOR)
                return new Vector2Int(c, r);
        }
        return new Vector2Int(-1, -1);
    }

    // -----------------------------------------------------------------------
    // 8. PINTAR el Tilemap de Unity
    // -----------------------------------------------------------------------
    void PaintTilemap()
    {
        tilemap.ClearAllTiles();

        for (int r = 0; r < mapHeight; r++)
            for (int c = 0; c < mapWidth; c++)
            {
                TileBase tile = CharToTile(map[r, c]);
                if (tile == null) continue;

                // Unity: eix Y invertit respecte a la nostra matriu
                var pos = new Vector3Int(c, mapHeight - 1 - r, 0);
                tilemap.SetTile(pos, tile);
            }
    }

    TileBase CharToTile(char ch) => ch switch
    {
        WALL => tileWall,
        FLOOR => tileFloor,
        DOOR => tileDoor,
        PLAYER => tilePlayer,
        FOOD => tileFood,
        POTION => tilePotion,
        GOLD => tileGold,
        STAIRS => tileStairs,
        WEAPON => tileWeapon,
        _ => null
    };

    // -----------------------------------------------------------------------
    // 9. EXPORTAR a .txt (format NetHack, com level01.txt)
    // -----------------------------------------------------------------------
    void ExportToTxt()
    {
        string folder = Path.Combine(Application.dataPath, "GeneratedMaps");
        Directory.CreateDirectory(folder);

        string path = Path.Combine(folder, outputFileName + ".txt");
        var sb = new StringBuilder();

        for (int r = 0; r < mapHeight; r++)
        {
            for (int c = 0; c < mapWidth; c++)
                sb.Append(map[r, c]);
            sb.AppendLine();
        }

        File.WriteAllText(path, sb.ToString(), Encoding.UTF8);
        Debug.Log($"[DungeonMapGenerator] Mapa exportat a: {path}");

#if UNITY_EDITOR
        AssetDatabase.Refresh();
#endif
    }

    // -----------------------------------------------------------------------
    // UTILS
    // -----------------------------------------------------------------------
    bool InBounds(int r, int c) =>
        r >= 0 && r < mapHeight && c >= 0 && c < mapWidth;
}


// ===========================================================================
// EDITOR PERSONALITZAT — botó "Generate & Export Map" a l'Inspector
// ===========================================================================
#if UNITY_EDITOR
[CustomEditor(typeof(DungeonMapGenerator))]
public class DungeonMapGeneratorEditor : Editor
{
    public override void OnInspectorGUI()
    {
        DrawDefaultInspector();

        GUILayout.Space(10);

        var gen = (DungeonMapGenerator)target;

        GUI.backgroundColor = new Color(0.4f, 0.8f, 0.4f);
        if (GUILayout.Button("▶  Generate & Export Map", GUILayout.Height(36)))
        {
            gen.GenerateAndExport();
        }
        GUI.backgroundColor = Color.white;

        GUILayout.Space(4);
        EditorGUILayout.HelpBox(
            "El fitxer .txt es desa a Assets/GeneratedMaps/\n" +
            "El Tilemap es pinta automàticament si hi ha tiles assignats.",
            MessageType.Info);
    }
}
#endif