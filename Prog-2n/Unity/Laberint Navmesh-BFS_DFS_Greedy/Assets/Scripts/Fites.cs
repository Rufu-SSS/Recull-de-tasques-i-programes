using System.Collections.Generic;
using System.Linq;
using UnityEngine;

public class Fites : MonoBehaviour
{
    [Header("Grid")]
    public int rows = 7;
    public int cols = 6;
    public int spacing = 5;
    [Range(0f, 0.45f)] public float wallProbability = 0.25f;

    [Header("Materials")]
    public Material startMat;
    public Material goalMat;
    public Material wallMat;
    public Material normalMat;

    private Node[,] grid;
    private Node startNode;
    private Node endNode;

    void Start()
    {
        GenerateGrid();
        SpawnPlayers();
    }

    void GenerateGrid()
    {
        grid = new Node[rows, cols];

        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < cols; j++)
            {
                bool walkable = Random.value > wallProbability;
                Node node = new Node(walkable);
                grid[i, j] = node;

                GameObject wp = GameObject.CreatePrimitive(PrimitiveType.Cube);
                wp.transform.position = new Vector3(i * spacing-35f, 0, j * spacing);
                wp.transform.localScale = new Vector3(4.8f, 0.2f, 4.8f);
                wp.name = $"Node_{i}_{j}";

                node.Waypoint = wp;
                wp.GetComponent<Renderer>().material = walkable ? normalMat : wallMat;
            }
        }

        startNode = grid[0, 0];
        endNode = grid[rows - 1, cols - 1];

        startNode.Walkable = true;
        endNode.Walkable = true;

        startNode.Waypoint.GetComponent<Renderer>().material = startMat;
        endNode.Waypoint.GetComponent<Renderer>().material = goalMat;
    }

    void SpawnPlayers()
    {
        SpawnPlayer(Color.blue, AStar(startNode, endNode), "A*");
        SpawnPlayer(Color.yellow, BFS(startNode, endNode), "BFS");
        SpawnPlayer(Color.magenta, DFS(startNode, endNode), "DFS");
        SpawnPlayer(Color.cyan, Greedy(startNode, endNode), "Greedy");
    }

    void SpawnPlayer(Color color, List<Node> path, string name)
    {
        if (path == null) return;

        GameObject p = GameObject.CreatePrimitive(PrimitiveType.Sphere);
        p.name = name;
        p.transform.position = startNode.Waypoint.transform.position + Vector3.up;
        p.GetComponent<Renderer>().material.color = color;

        var pm = p.AddComponent<PlayerMovement>();
        pm.Initialize(path, 4f, 6f, 0.3f);
    }

    #region PATHFINDING

    List<Node> AStar(Node start, Node end)
    {
        ResetNodes();
        List<Node> open = new List<Node>();
        HashSet<Node> closed = new HashSet<Node>();

        start.G = 0;
        start.H = GetDistance(start, end);
        open.Add(start);

        while (open.Count > 0)
        {
            Node current = open.OrderBy(n => n.F).First();
            open.Remove(current);
            closed.Add(current);

            if (current == end)
                return RetracePath(start, end);

            foreach (Node n in GetNeighbours(current))
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

    List<Node> BFS(Node start, Node end)
    {
        ResetNodes();
        Queue<Node> q = new Queue<Node>();
        q.Enqueue(start);

        while (q.Count > 0)
        {
            Node c = q.Dequeue();
            if (c == end) return RetracePath(start, end);

            foreach (Node n in GetNeighbours(c))
            {
                if (!n.Visited)
                {
                    n.Visited = true;
                    n.Parent = c;
                    q.Enqueue(n);
                }
            }
        }
        return null;
    }

    List<Node> DFS(Node start, Node end)
    {
        ResetNodes();
        Stack<Node> s = new Stack<Node>();
        s.Push(start);

        while (s.Count > 0)
        {
            Node c = s.Pop();
            if (c.Visited) continue;
            c.Visited = true;

            if (c == end) return RetracePath(start, end);

            foreach (Node n in GetNeighbours(c))
            {
                if (!n.Visited)
                {
                    n.Parent = c;
                    s.Push(n);
                }
            }
        }
        return null;
    }

    List<Node> Greedy(Node start, Node end)
    {
        ResetNodes();
        Node current = start;
        List<Node> path = new List<Node>();

        while (current != end)
        {
            path.Add(current);
            current.Visited = true;

            Node next = GetNeighbours(current)
                .OrderBy(n => GetDistance(n, end) + Random.Range(0f, 1.5f))
                .FirstOrDefault(n => !n.Visited);

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
        foreach (Node n in grid)
            n.Reset();
    }

    List<Node> RetracePath(Node start, Node end)
    {
        List<Node> path = new List<Node>();
        Node c = end;
        while (c != start)
        {
            path.Add(c);
            c = c.Parent;
        }
        path.Reverse();
        return path;
    }

    List<Node> GetNeighbours(Node node)
    {
        Vector2Int p = GetPos(node);
        List<Node> n = new List<Node>();

        if (p.x > 0) n.Add(grid[p.x - 1, p.y]);
        if (p.x < rows - 1) n.Add(grid[p.x + 1, p.y]);
        if (p.y > 0) n.Add(grid[p.x, p.y - 1]);
        if (p.y < cols - 1) n.Add(grid[p.x, p.y + 1]);

        return n.Where(x => x.Walkable).ToList();
    }

    Vector2Int GetPos(Node node)
    {
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                if (grid[i, j] == node)
                    return new Vector2Int(i, j);
        return Vector2Int.zero;
    }

    float GetDistance(Node a, Node b)
    {
        return Vector3.Distance(a.Waypoint.transform.position, b.Waypoint.transform.position);
    }

    #endregion
}
