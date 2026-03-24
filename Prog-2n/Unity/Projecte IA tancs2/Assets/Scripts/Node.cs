using System.Collections.Generic;
using UnityEngine;

public class Node : MonoBehaviour
{
    private int profunditat;
    private bool permesa;
    private GameObject fites = new GameObject();
    private List<Node> veins = new List<Node>();

    public int Depth { get => profunditat; set => profunditat = value; }
    public bool Walkable { get => permesa; set => permesa = value; }
    public GameObject Waypoint { get => fites; set => fites = value; }
    public List<Node> Neighbors { get => veins; set => veins = value; }

    public Node()
    {
        this.profunditat = -1;
        this.permesa = true;
    }

    public Node(bool permesa)
    {
        this.profunditat = -1;
        this.permesa = permesa;
    }

    public override bool Equals(System.Object obj)
    {
        if (obj == null) return false;
        Node n = obj as Node;
        if ((System.Object)n == null) return false;

        return this.fites.transform.position.x == n.Waypoint.transform.position.x &&
               this.fites.transform.position.z == n.Waypoint.transform.position.z;
    }
}
