using UnityEngine;

public class ParallaxController : MonoBehaviour
{
    private float _length, _startPos;
    public GameObject cam;
    [Range(0f, 1f)]
    public float parallaxEffect = 0.5f;

    void Start()
    {
        _startPos = transform.position.x;
        _length = GetComponent<SpriteRenderer>().bounds.size.x;
    }

    void Update()
    {
        float temp = cam.transform.position.x * (1 - parallaxEffect);
        float dist = cam.transform.position.x * parallaxEffect;

        transform.position = new Vector3(_startPos + dist, transform.position.y, transform.position.z);

        if (temp > _startPos + _length)
            _startPos += _length;
        else if (temp < _startPos - _length)
            _startPos -= _length;
    }
}