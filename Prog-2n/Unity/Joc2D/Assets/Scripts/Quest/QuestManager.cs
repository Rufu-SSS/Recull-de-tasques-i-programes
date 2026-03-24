using UnityEngine;

public class QuestManager : MonoBehaviour
{
    private static QuestManager ThisInstance = null;

    public Quest[] Quests;

    void Awake()
    {
        if (ThisInstance == null)
        {
            DontDestroyOnLoad(this);
            ThisInstance = this;
        }
        else
        {
            DestroyImmediate(gameObject);
        }
    }

    public static Quest.QUESTSTATUS GetQuestStatus(string QuestName)
    {
        foreach (Quest Q in ThisInstance.Quests)
        {
            if (Q.QuestName.Equals(QuestName))
            {
                return Q.Status;
            }
        }
        return Quest.QUESTSTATUS.UNASSIGNED;
    }

    public static void SetQuestStatus(string QuestName, Quest.QUESTSTATUS NewStatus)
    {
        foreach (Quest Q in ThisInstance.Quests)
        {
            if (Q.QuestName.Equals(QuestName))
            {
                Q.Status = NewStatus;
                return;
            }
        }
    }

    public static void Reset()
    {
        foreach (Quest Q in ThisInstance.Quests)
        {
            Q.Status = Quest.QUESTSTATUS.UNASSIGNED;
        }
    }
}