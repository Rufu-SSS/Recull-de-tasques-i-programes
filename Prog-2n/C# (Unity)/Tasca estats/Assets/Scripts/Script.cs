using UnityEngine;

public enum EnemyState
{
    Patrol,
    Chase,
    Attack,
    Stop
}


public class Script : MonoBehaviour
{
    public EnemyState currentState=EnemyState.Patrol;
    public Transform player;
    public float chaseDistance = 10f;
    public float patrolDistance = 20f;
    public float attackDistance = 2f;

    private void Update()
    {
        float distanceToPlayer=Vector3.Distance(transform.position, player.position);
        UpdateState(distanceToPlayer);
        PerformAction();
    }
    // Calcula la distància amb el jugador

    void UpdateState(float distance)
    {
        if (distance <= attackDistance)
        {
            currentState = EnemyState.Attack;
        }
        else if (distance <= chaseDistance)
        {
            currentState = EnemyState.Chase;
        }
        else if (distance <= patrolDistance)
        {
            currentState = EnemyState.Patrol;
        }
        else
        {
            currentState = EnemyState.Stop;
        }
    }
    // Funció que canvia l'estat segons unes variables o condicions, la distància

    void PerformAction()
    {
        switch (currentState)
        {
            case EnemyState.Patrol:
                Patrol();
                break;
            case EnemyState.Chase:
                Chase();
                break;
            case EnemyState.Attack:
                Attack();
                break;
            case EnemyState.Stop:
                Stop();
                break;
        }
    }
    // Les funcions d'estat, patrol, chase, attack i stop
    void Patrol()
    {
        Debug.Log("Enemy is patrolling");
    }
    void Chase()
    {
        Debug.Log("Enemy is chasing the player");
        transform.position = Vector3.MoveTowards(
            transform.position,
            player.position,
            3f * Time.deltaTime
            );
    }
    void Attack()
    {
        Debug.Log("Enemy is attacking");
    }
    void Stop()
    {
        Debug.Log("Too far away, i ain't doing allat");
    }
    // Aquesta nova funció revisa que el jugador no es trobi prou lluny
    // com per rendir-se i deixar de perseguir-lo
}
