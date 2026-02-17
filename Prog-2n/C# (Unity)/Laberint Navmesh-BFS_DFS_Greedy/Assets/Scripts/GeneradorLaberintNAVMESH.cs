using System.Collections.Generic;
using System.Linq;
using Unity.AI.Navigation;
using UnityEngine;
using UnityEngine.AI;

public class LaberintNavMeshComplet : MonoBehaviour
{
    [Header("Configuració del Laberint")]
    public int rows = 10;
    public int cols = 10;
    public float cellSize = 4f;
    [Range(0f, 1f)] public float wallProbability = 0.2f;

    [Header("Referències NavMesh")]
    public NavMeshSurface surface;

    [Header("Materials")]
    public Material floorMat;
    public Material wallMat;
    public Material startMat;
    public Material goalMat;
    public Material dfsMat, bfsMat, greedyMat, aStarMat;

    [Header("Configuració Agents")]
    public float agentSpeed = 8f;
    public float agentAcceleration = 20f;

    private NavMeshNode[,] grid;
    private NavMeshNode startNode;
    private NavMeshNode endNode;
    private Vector3 startPos;
    private Vector3 endPos;

    void Start()
    {
        GenerarLaberint();
        ConstruirNavMesh();
        SpawnAgents();
    }

    void GenerarLaberint()
    {
        grid = new NavMeshNode[rows, cols];

        // Crear terra
        GameObject floor = GameObject.CreatePrimitive(PrimitiveType.Cube);
        floor.name = "Terra";
        floor.transform.localScale = new Vector3(rows * cellSize, 1f, cols * cellSize);
        floor.transform.position = new Vector3((rows * cellSize) / 2 - (cellSize / 2), -0.5f, (cols * cellSize) / 2 - (cellSize / 2));
        if (floorMat != null) floor.GetComponent<Renderer>().material = floorMat;
        floor.transform.SetParent(surface.transform);

        // Generar grid amb parets
        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < cols; j++)
            {
                bool isStart = (i == 0 && j == 0);
                bool isEnd = (i == rows - 1 && j == cols - 1);
                bool walkable = isStart || isEnd || Random.value > wallProbability;

                Vector3 pos = CalculatePosition(i, j);
                NavMeshNode node = new NavMeshNode(walkable, pos);
                grid[i, j] = node;

                if (!walkable)
                {
                    GameObject wall = GameObject.CreatePrimitive(PrimitiveType.Cube);
                    wall.name = $"Paret_{i}_{j}";
                    wall.transform.position = pos + Vector3.up * 1.5f;
                    wall.transform.localScale = new Vector3(cellSize * 0.9f, 4f, cellSize * 0.9f);
                    if (wallMat != null) wall.GetComponent<Renderer>().material = wallMat;
                    wall.transform.SetParent(surface.transform);
                    node.Wall = wall;
                }
                else
                {
                    // Crear marcador visual per nodes caminables
                    GameObject marker = GameObject.CreatePrimitive(PrimitiveType.Cube);
                    marker.name = $"Node_{i}_{j}";
                    marker.transform.position = pos + Vector3.up * 0.1f;
                    marker.transform.localScale = new Vector3(cellSize * 0.3f, 0.1f, cellSize * 0.3f);
                    marker.GetComponent<Renderer>().material = floorMat;
                    marker.transform.SetParent(surface.transform);
                    node.Marker = marker;
                }

                if (isStart)
                {
                    startNode = node;
                    startPos = pos;
                    if (node.Marker != null && startMat != null)
                        node.Marker.GetComponent<Renderer>().material = startMat;
                }
                if (isEnd)
                {
                    endNode = node;
                    endPos = pos;
                    if (node.Marker != null && goalMat != null)
                        node.Marker.GetComponent<Renderer>().material = goalMat;
                }
            }
        }
    }

    Vector3 CalculatePosition(int x, int z)
    {
        return new Vector3(x * cellSize, 0, z * cellSize);
    }

    void ConstruirNavMesh()
    {
        surface.BuildNavMesh();
    }

    void SpawnAgents()
    {
        SpawnAgent("A*", aStarMat, AStar(startNode, endNode));
        SpawnAgent("BFS", bfsMat, BFS(startNode, endNode));
        SpawnAgent("DFS", dfsMat, DFS(startNode, endNode));
        SpawnAgent("Greedy", greedyMat, Greedy(startNode, endNode));
    }

    void SpawnAgent(string name, Material mat, List<NavMeshNode> path)
    {
        if (path == null || path.Count == 0)
        {
            Debug.LogWarning($"No s'ha trobat camí per {name}");
            return;
        }

        GameObject agentObj = GameObject.CreatePrimitive(PrimitiveType.Capsule);
        agentObj.name = name + " Agent";
        agentObj.transform.position = startPos + Vector3.up;
        agentObj.transform.localScale = Vector3.one * (cellSize * 0.4f);

        if (mat != null) agentObj.GetComponent<Renderer>().material = mat;

        NavMeshAgent agent = agentObj.AddComponent<NavMeshAgent>();
        agent.speed = agentSpeed;
        agent.acceleration = agentAcceleration;
        agent.radius = cellSize * 0.15f;

        NavMeshAgentController controller = agentObj.AddComponent<NavMeshAgentController>();
        controller.Initialize(path);
    }

    #region PATHFINDING

    List<NavMeshNode> AStar(NavMeshNode start, NavMeshNode end)
    {
        ResetNodes();
        List<NavMeshNode> open = new List<NavMeshNode>();
        HashSet<NavMeshNode> closed = new HashSet<NavMeshNode>();

        start.G = 0;
        start.H = GetDistance(start, end);
        open.Add(start);

        while (open.Count > 0)
        {
            NavMeshNode current = open.OrderBy(n => n.F).First();
            open.Remove(current);
            closed.Add(current);

            if (current == end)
                return RetracePath(start, end);

            foreach (NavMeshNode n in GetNeighbours(current))
            {
                if (!n.Walkable || closed.Contains(n)) continue;

                float tentativeG = current.G + GetDistance(current, n);
                if (tentativeG < n.G)
                {
                    n.Parent = current;
                    n.G = tentativeG;
                    n.H = GetDistance(n, end);
                    if (!open.Contains(n)) open.Add(n);
                }
            }
        }
        return null;
    }

    List<NavMeshNode> BFS(NavMeshNode start, NavMeshNode end)
    {
        ResetNodes();
        Queue<NavMeshNode> q = new Queue<NavMeshNode>();
        q.Enqueue(start);
        start.Visited = true;

        while (q.Count > 0)
        {
            NavMeshNode c = q.Dequeue();
            if (c == end) return RetracePath(start, end);

            foreach (NavMeshNode n in GetNeighbours(c))
            {
                if (n.Walkable && !n.Visited)
                {
                    n.Visited = true;
                    n.Parent = c;
                    q.Enqueue(n);
                }
            }
        }
        return null;
    }

    List<NavMeshNode> DFS(NavMeshNode start, NavMeshNode end)
    {
        ResetNodes();
        Stack<NavMeshNode> s = new Stack<NavMeshNode>();
        s.Push(start);

        while (s.Count > 0)
        {
            NavMeshNode c = s.Pop();
            if (c.Visited) continue;
            c.Visited = true;

            if (c == end) return RetracePath(start, end);

            foreach (NavMeshNode n in GetNeighbours(c))
            {
                if (n.Walkable && !n.Visited)
                {
                    n.Parent = c;
                    s.Push(n);
                }
            }
        }
        return null;
    }

    List<NavMeshNode> Greedy(NavMeshNode start, NavMeshNode end)
    {
        ResetNodes();
        NavMeshNode current = start;
        List<NavMeshNode> path = new List<NavMeshNode>();

        while (current != end)
        {
            path.Add(current);
            current.Visited = true;

            NavMeshNode next = GetNeighbours(current)
                .Where(n => n.Walkable && !n.Visited)
                .OrderBy(n => GetDistance(n, end) + Random.Range(0f, 0.5f))
                .FirstOrDefault();

            if (next == null) return null;
            next.Parent = current;
            current = next;
        }
        path.Add(end);
        return path;
    }

    #endregion

    #region HELPERS

    void ResetNodes()
    {
        foreach (NavMeshNode n in grid)
            n.Reset();
    }

    List<NavMeshNode> RetracePath(NavMeshNode start, NavMeshNode end)
    {
        List<NavMeshNode> path = new List<NavMeshNode>();
        NavMeshNode c = end;
        while (c != start)
        {
            path.Add(c);
            c = c.Parent;
        }
        path.Reverse();
        return path;
    }

    List<NavMeshNode> GetNeighbours(NavMeshNode node)
    {
        Vector2Int p = GetPos(node);
        List<NavMeshNode> n = new List<NavMeshNode>();

        if (p.x > 0) n.Add(grid[p.x - 1, p.y]);
        if (p.x < rows - 1) n.Add(grid[p.x + 1, p.y]);
        if (p.y > 0) n.Add(grid[p.x, p.y - 1]);
        if (p.y < cols - 1) n.Add(grid[p.x, p.y + 1]);

        return n;
    }

    Vector2Int GetPos(NavMeshNode node)
    {
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                if (grid[i, j] == node)
                    return new Vector2Int(i, j);
        return Vector2Int.zero;
    }

    float GetDistance(NavMeshNode a, NavMeshNode b)
    {
        return Vector3.Distance(a.Position, b.Position);
    }

    #endregion
}

#region CLASSES

public class NavMeshNode
{
    public Vector3 Position { get; set; }
    public bool Walkable { get; set; }
    public GameObject Wall { get; set; }
    public GameObject Marker { get; set; }
    public NavMeshNode Parent { get; set; }

    // A*
    public float G { get; set; }
    public float H { get; set; }
    public float F => G + H;

    // DFS / BFS
    public bool Visited { get; set; }

    public NavMeshNode(bool walkable, Vector3 position)
    {
        Walkable = walkable;
        Position = position;
        Reset();
    }

    public void Reset()
    {
        Parent = null;
        G = float.MaxValue;
        H = 0;
        Visited = false;
    }
}

public class NavMeshAgentController : MonoBehaviour
{
    private List<NavMeshNode> path;
    private int currentIndex = 0;
    private NavMeshAgent agent;

    public void Initialize(List<NavMeshNode> nodePath)
    {
        path = nodePath;
        agent = GetComponent<NavMeshAgent>();

        if (path != null && path.Count > 0)
        {
            agent.SetDestination(path[0].Position);
        }
    }

    void Update()
    {
        if (path == null || currentIndex >= path.Count) return;

        if (!agent.pathPending && agent.remainingDistance < 0.5f)
        {
            currentIndex++;
            if (currentIndex < path.Count)
            {
                agent.SetDestination(path[currentIndex].Position);
            }
        }
    }
}

#endregion