using System.Collections;
using System.Collections.Generic;
using Unity.VisualScripting;
using UnityEngine;
public class MoveForward : MonoBehaviour {
    public float Speed = 15.0f;
    [HideInInspector]
    public Vector3 LookDirection;
    private void Start() {
        LookDirection = transform.forward; }
    void Update() { transform.position += LookDirection * (Speed * Time.deltaTime); } }