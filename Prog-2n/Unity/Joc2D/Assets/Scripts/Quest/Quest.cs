using UnityEngine;

[System.Serializable]
public class Quest
{
    public string QuestName;

    public enum QUESTSTATUS
    {
        UNASSIGNED,
        ASSIGNED,
        COMPLETE
    }

    public QUESTSTATUS Status = QUESTSTATUS.UNASSIGNED;
}