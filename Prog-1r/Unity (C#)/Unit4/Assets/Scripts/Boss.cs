using System.Collections;
using UnityEngine;
public class Boss : MonoBehaviour {
    public float Speed = 8.0f;
    public float ChargeSpeed = 20.0f;
    public float Health = 3.0f; // Aguanta 3 cops de powerup
    public float ChargeCooldown = 4.0f;
    private Rigidbody _rigidbody;
    private GameObject _player;
    private bool _isCharging = false;
    void Start() {
        _player = GameObject.Find("Player");
        _rigidbody = GetComponent<Rigidbody>();
        StartCoroutine(ChargeRoutine()); }
    void Update() {
        if (_player == null) return;
        if (!_isCharging) {
            // Moviment normal cap al jugador
            Vector3 direction = (_player.transform.position - transform.position).normalized;
            _rigidbody.AddForce(direction * Speed); }
        if (transform.position.y < -10.0f)
            Destroy(gameObject); }
    private IEnumerator ChargeRoutine(){
        while (true) {
            yield return new WaitForSeconds(ChargeCooldown);
            if (_player == null) yield break;
            // Embestida cap al jugador
            _isCharging = true;
            Vector3 chargeDirection = (_player.transform.position - transform.position).normalized;
            _rigidbody.AddForce(chargeDirection * ChargeSpeed, ForceMode.Impulse);
            yield return new WaitForSeconds(0.5f);
            _isCharging = false; } }
    // El boss aguanta cops — es destrueix quan arriba a 0 de vida
    private void OnCollisionEnter(Collision collision) {
        if (collision.gameObject.CompareTag("Player")) {
            Health--;
            if (Health <= 0)
                Destroy(gameObject); } } }