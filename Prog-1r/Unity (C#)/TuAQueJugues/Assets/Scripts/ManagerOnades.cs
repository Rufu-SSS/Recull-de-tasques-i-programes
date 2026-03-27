using UnityEngine;
using System.Collections;
using System.Collections.Generic;

// CONCEPTE 4: Polimorfisme
// La llista és de tipus Nau (classe base), però conté
// NauEnemiga i NauEnemigaElite. Cada una actua diferent.

public class GestorOnades : MonoBehaviour {
    public GameObject PrefabEnemic;
    public GameObject PrefabEnemicElit;
    public float TempsEntreOnades = 3f;   // segons entre onada i onada
    public int EnemicsPerOnada = 3;        // enemics normals per onada
    public bool SpawnaElits = true;        // activa/desactiva elits
    private List<Nau> _totsElsEnemics = new List<Nau>();
    private int _numeroOnada = 0;
    void Start() {
        StartCoroutine(BucleDOnades());
    }
    IEnumerator BucleDOnades() {
        while (true) {
            _numeroOnada++;
            Debug.Log($"--- Onada {_numeroOnada} ---");
            LlançarOnada();
            // Espera que tots els enemics morin O un temps màxim
            yield return StartCoroutine(EsperarFiOnada());
            Debug.Log($"Onada {_numeroOnada} completada! Propera en {TempsEntreOnades}s");
            yield return new WaitForSeconds(TempsEntreOnades);
            // Cada 3 onades augmenta la dificultat
            if (_numeroOnada % 3 == 0) {
                EnemicsPerOnada++;
                TempsEntreOnades = Mathf.Max(1f, TempsEntreOnades - 0.5f);
                Debug.Log($"Dificultat augmentada! Enemics: {EnemicsPerOnada}");
            }
        }
    }
    void LlançarOnada() {
        _totsElsEnemics.Clear();
        // Spawn enemics normals repartits per la pantalla
        for (int i = 0; i < EnemicsPerOnada; i++) {
            Vector3 pos = new Vector3(Random.Range(-4f, 4f), 7f, 0f);
            GameObject go = Instantiate(PrefabEnemic, pos, Quaternion.identity);
            Nau nau = go.GetComponent<NauEnemiga>();
            if (nau != null) _totsElsEnemics.Add(nau);
        }
        // Spawn elit cada 2 onades
        if (SpawnaElits && _numeroOnada % 2 == 0) {
            Vector3 posElit = new Vector3(0f, 7f, 0f);
            GameObject elit = Instantiate(PrefabEnemicElit, posElit, Quaternion.identity);
            Nau nau = elit.GetComponent<NauEnemigaElite>();
            if (nau != null) _totsElsEnemics.Add(nau);
        }
    }
    IEnumerator EsperarFiOnada() {
        float tempsMaxim = 15f; // si en 15s no moren tots, passa a la següent
        float temps = 0f;
        while (temps < tempsMaxim) {
            // Elimina els nulls (enemics destruïts) de la llista
            _totsElsEnemics.RemoveAll(nau => nau == null);
            if (_totsElsEnemics.Count == 0)
                yield break; // tots morts, acaba l'espera

            temps += Time.deltaTime;
            yield return null;
        }
        Debug.Log("Temps esgotat, nova onada!");
    }
}