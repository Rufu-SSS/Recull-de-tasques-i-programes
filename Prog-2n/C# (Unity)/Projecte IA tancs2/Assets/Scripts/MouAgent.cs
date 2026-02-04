using System.Collections.Generic;
using UnityEngine;

public class MouAgent : MonoBehaviour
{
    public Node[,] grid;
    List<Node> path = new List<Node>();
    int curNode = 0;

    public GameObject prefabWaypoint;
    public Material goalMat;
    public Material wallMat;

    Vector3 goal;
    float speed = 4.0f;
    float distanciaMin = 0.5f;
    float rotationSpeed = 4f;

    int spacing = 5;

    Node startNode;
    Node endNode;

    void Start()
    {
        GenerateGrid();
        PositionAgent();
    }

    void Update()
    {
        if (Input.GetKeyDown(KeyCode.R))
        {
            ClearGrid();
            Start();
        }

        if (Input.GetKeyDown(KeyCode.Return))
        {
            PositionAgent();
            curNode = 0;
        }

        if (Input.GetKeyDown(KeyCode.P))
        {
            path = BFS(startNode, endNode);
            printMyPath();
        }

        if (Input.GetKeyDown(KeyCode.Alpha1))
        {
            path = DFS(startNode, endNode);
            printMyPath();
        }

        if (Input.GetKeyDown(KeyCode.Alpha2))
        {
            path = RandomWalk(startNode, endNode);
            printMyPath();
        }

        if (path.Count == 0) return;

        goal = new Vector3(path[curNode].Waypoint.transform.position.x,
                           this.transform.position.y,
                           path[curNode].Waypoint.transform.position.z);

        Vector3 direction = goal - this.transform.position;

        if (direction.magnitude > distanciaMin)
        {
            this.transform.rotation = Quaternion.Slerp(
                this.transform.rotation,
                Quaternion.LookRotation(direction),
                Time.deltaTime * rotationSpeed);

            this.transform.Translate(0, 0, speed * Time.deltaTime);
        }
        else
        {
            if (curNode < path.Count - 1)
                curNode++;
        }
    }

    void GenerateGrid()
    {
        grid = new Node[7, 6];

        for (int i = 0; i < grid.GetLength(0); i++)
        {
            for (int j = 0; j < grid.GetLength(1); j++)
            {
                bool walkable = Random.value > 0.25f;

                grid[i, j] = new Node(walkable);

                grid[i, j].Waypoint = Instantiate(prefabWaypoint,
                    new Vector3(i * spacing, this.transform.position.y - 0.6f, j * spacing),
                    Quaternion.identity);

                if (!grid[i, j].Walkable)
                {
                    grid[i, j].Waypoint.GetComponent<Renderer>().material = wallMat;
                }
            }
        }

        for (int i = 0; i < grid.GetLength(0); i++)
            for (int j = 0; j < grid.GetLength(1); j++)
                if (grid[i, j].Walkable)
                    grid[i, j].Neighbors = getAdjacentNodes(grid, i, j);

        startNode = grid[0, 0];
        endNode = grid[6, 5];

        startNode.Walkable = true;
        endNode.Walkable = true;

        endNode.Waypoint.GetComponent<Renderer>().material = goalMat;
    }

    void PositionAgent()
    {
        this.transform.position = new Vector3(startNode.Waypoint.transform.position.x,
                                              this.transform.position.y,
                                              startNode.Waypoint.transform.position.z);
    }

    void ClearGrid()
    {
        foreach (Node n in grid)
            Destroy(n.Waypoint);
    }

    List<Node> getAdjacentNodes(Node[,] graf, int fila, int col)
    {
        List<Node> llista = new List<Node>();

        if (fila - 1 >= 0 && graf[fila - 1, col].Walkable)
            llista.Add(graf[fila - 1, col]);
        if (fila + 1 < graf.GetLength(0) && graf[fila + 1, col].Walkable)
            llista.Add(graf[fila + 1, col]);
        if (col - 1 >= 0 && graf[fila, col - 1].Walkable)
            llista.Add(graf[fila, col - 1]);
        if (col + 1 < graf.GetLength(1) && graf[fila, col + 1].Walkable)
            llista.Add(graf[fila, col + 1]);

        return llista;
    }

    List<Node> BFS(Node start, Node end)
    {
        Queue<Node> toVisit = new Queue<Node>();
        List<Node> visited = new List<Node>();

        Node current = start;
        current.Depth = 0;

        toVisit.Enqueue(current);

        List<Node> finalPath = new List<Node>();

        while (toVisit.Count > 0)
        {
            current = toVisit.Dequeue();

            if (visited.Contains(current)) continue;
            visited.Add(current);

            if (current.Equals(end))
            {
                while (current.Depth != 0)
                {
                    foreach (Node n in current.Neighbors)
                    {
                        if (n.Depth == current.Depth - 1)
                        {
                            finalPath.Add(current);
                            current = n;
                            break;
                        }
                    }
                }
                finalPath.Reverse();
                return finalPath;
            }

            foreach (Node n in current.Neighbors)
            {
                if (!visited.Contains(n) && n.Walkable)
                {
                    n.Depth = current.Depth + 1;
                    toVisit.Enqueue(n);
                }
            }
        }
        return finalPath;
    }

    List<Node> DFS(Node start, Node end)
    {
        Stack<Node> stack = new Stack<Node>();
        Dictionary<Node, Node> parent = new Dictionary<Node, Node>();
        HashSet<Node> visited = new HashSet<Node>();

        stack.Push(start);
        visited.Add(start);

        while (stack.Count > 0)
        {
            Node current = stack.Pop();

            if (current == end)
                return ReconstructPath(parent, end);

            foreach (Node neigh in current.Neighbors)
            {
                if (!visited.Contains(neigh) && neigh.Walkable)
                {
                    visited.Add(neigh);
                    parent[neigh] = current;
                    stack.Push(neigh);
                }
            }
        }

        return new List<Node>();
    }

    List<Node> ReconstructPath(Dictionary<Node, Node> parent, Node end)
    {
        List<Node> path = new List<Node>();
        Node current = end;

        while (parent.ContainsKey(current))
        {
            path.Add(current);
            current = parent[current];
        }

        path.Reverse();
        return path;
    }

    List<Node> RandomWalk(Node start, Node end, int maxSteps = 200)
    {
        List<Node> path = new List<Node>();
        Node current = start;

        path.Add(current);

        for (int i = 0; i < maxSteps; i++)
        {
            if (current == end)
                break;

            List<Node> accessibles = new List<Node>();

            foreach (Node n in current.Neighbors)
                if (n.Walkable)
                    accessibles.Add(n);

            if (accessibles.Count == 0)
                break;

            Node next = accessibles[Random.Range(0, accessibles.Count)];
            path.Add(next);
            current = next;
        }

        return path;
    }

    void printMyPath()
    {
        for (int i = 0; i < path.Count; i++)
            Debug.Log("PATH[" + i + "] → " + path[i].Waypoint.transform.position);
    }
}
