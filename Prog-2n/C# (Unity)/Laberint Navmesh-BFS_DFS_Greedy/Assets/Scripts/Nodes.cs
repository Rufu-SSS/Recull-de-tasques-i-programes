using UnityEngine;

public class Node
{
    public GameObject Waypoint { get; set; }
    public bool Walkable { get; set; }
    public Node Parent { get; set; }

    // A*
    public float G { get; set; }
    public float H { get; set; }
    public float F => G + H;

    // DFS / control
    public bool Visited { get; set; }

    public Node(bool walkable = true)
    {
        Walkable = walkable;
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
