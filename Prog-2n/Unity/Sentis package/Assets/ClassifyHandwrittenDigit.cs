using Unity.InferenceEngine;
using UnityEngine;
using UnityEngine.UI;
using TMPro;

public class MagicDigitGame : MonoBehaviour
{
    [Header("IA Configuration")]
    public ModelAsset modelAsset;

    [Header("UI Elements")]
    public RawImage drawingCanvas;
    public TextMeshProUGUI predictionText;
    public TextMeshProUGUI confidenceText;
    public TextMeshProUGUI instructionText;
    public Button clearButton;
    public Button predictButton;

    [Header("Drawing")]
    public int textureSize = 280; // Més gran per dibuixar millor
    private Texture2D drawingTexture;
    private Texture2D processedTexture;
    private bool isDrawing = false;
    private Vector2 lastMousePos;

    [Header("IA")]
    private Model runtimeModel;
    private Worker worker;
    private float[] results;

    [Header("Game Stats")]
    public int correctPredictions = 0;
    public int totalAttempts = 0;
    public TextMeshProUGUI scoreText;

    void Start()
    {
        // Inicialitzar el model d'IA
        InitializeModel();

        // Crear textura per dibuixar
        drawingTexture = new Texture2D(textureSize, textureSize);
        ClearCanvas();
        drawingCanvas.texture = drawingTexture;

        // Configurar botons
        clearButton.onClick.AddListener(ClearCanvas);
        predictButton.onClick.AddListener(PredictDigit);

        // Text inicial
        instructionText.text = "Dibuixa un número del 0 al 9!";
        UpdateScore();
    }

    void InitializeModel()
    {
        Model sourceModel = ModelLoader.Load(modelAsset);

        FunctionalGraph graph = new FunctionalGraph();
        FunctionalTensor[] inputs = graph.AddInputs(sourceModel);
        FunctionalTensor[] outputs = Functional.Forward(sourceModel, inputs);
        FunctionalTensor softmax = Functional.Softmax(outputs[0]);

        runtimeModel = graph.Compile(softmax);
        worker = new Worker(runtimeModel, BackendType.GPUCompute);
    }

    void Update()
    {
        // Detectar dibuix amb el ratolí
        if (Input.GetMouseButtonDown(0))
        {
            isDrawing = true;
            lastMousePos = GetCanvasPosition(Input.mousePosition);
        }
        else if (Input.GetMouseButtonUp(0))
        {
            isDrawing = false;
        }

        if (isDrawing && Input.GetMouseButton(0))
        {
            Vector2 currentPos = GetCanvasPosition(Input.mousePosition);
            DrawLine(lastMousePos, currentPos);
            lastMousePos = currentPos;
        }
    }

    Vector2 GetCanvasPosition(Vector3 mousePosition)
    {
        RectTransform rectTransform = drawingCanvas.rectTransform;
        Vector2 localPoint;
        RectTransformUtility.ScreenPointToLocalPointInRectangle(
            rectTransform,
            mousePosition,
            null,
            out localPoint
        );

        // Convertir a coordenades de textura
        Vector2 normalizedPoint = Rect.PointToNormalized(rectTransform.rect, localPoint);
        return new Vector2(
            normalizedPoint.x * textureSize,
            normalizedPoint.y * textureSize
        );
    }

    void DrawLine(Vector2 from, Vector2 to)
    {
        int steps = Mathf.CeilToInt(Vector2.Distance(from, to));
        int brushSize = 5; // Gruix del pinzell

        for (int i = 0; i <= steps; i++)
        {
            float t = i / (float)steps;
            Vector2 point = Vector2.Lerp(from, to, t);

            // Dibuixar cercle per fer el traç més suau
            for (int x = -brushSize; x <= brushSize; x++)
            {
                for (int y = -brushSize; y <= brushSize; y++)
                {
                    if (x * x + y * y <= brushSize * brushSize)
                    {
                        int px = Mathf.Clamp((int)point.x + x, 0, textureSize - 1);
                        int py = Mathf.Clamp((int)point.y + y, 0, textureSize - 1);
                        // Dibuixar en blanc sobre fons negre (recomanat)
                        drawingTexture.SetPixel(px, py, Color.white);
                        // drawingTexture.SetPixel(px, py, Color.black); // Per fons blanc
                    }
                }
            }
        }

        drawingTexture.Apply();
    }

    void ClearCanvas()
    {
        Color[] clearColors = new Color[textureSize * textureSize];
        for (int i = 0; i < clearColors.Length; i++)
        {
            clearColors[i] = Color.black; // Fons negre (recomanat)
            // clearColors[i] = Color.white; // Descomenta per fons blanc
        }
        drawingTexture.SetPixels(clearColors);
        drawingTexture.Apply();

        predictionText.text = "?";
        confidenceText.text = "";
    }

    void PredictDigit()
    {
        // Processar imatge a 28x28
        processedTexture = ResizeTexture(drawingTexture, 28, 28);

        // Crear tensor d'entrada
        using Tensor inputTensor = TextureConverter.ToTensor(processedTexture, width: 28, height: 28, channels: 1);

        // Executar el model
        worker.Schedule(inputTensor);
        Tensor<float> outputTensor = worker.PeekOutput() as Tensor<float>;
        results = outputTensor.DownloadToArray();

        // Trobar el dígit amb més probabilitat
        int predictedDigit = 0;
        float maxConfidence = 0f;

        for (int i = 0; i < results.Length; i++)
        {
            if (results[i] > maxConfidence)
            {
                maxConfidence = results[i];
                predictedDigit = i;
            }
        }

        // Mostrar resultats amb efecte dramàtic
        StartCoroutine(RevealPrediction(predictedDigit, maxConfidence));
    }

    System.Collections.IEnumerator RevealPrediction(int digit, float confidence)
    {
        instructionText.text = "La IA està pensant...";
        yield return new WaitForSeconds(1f);

        predictionText.text = digit.ToString();
        confidenceText.text = $"Confiança: {(confidence * 100):F1}%";
        instructionText.text = $"La IA diu: És un {digit}!";

        // Mostrar totes les probabilitats (opcional, per debug)
        string allResults = "Probabilitats:\n";
        for (int i = 0; i < results.Length; i++)
        {
            allResults += $"{i}: {(results[i] * 100):F1}%\n";
        }
        Debug.Log(allResults);

        totalAttempts++;
        if (confidence > 0.8f) correctPredictions++;
        UpdateScore();
    }

    void UpdateScore()
    {
        float accuracy = totalAttempts > 0 ? (correctPredictions / (float)totalAttempts * 100f) : 0f;
        scoreText.text = $"Precisió: {accuracy:F1}% ({correctPredictions}/{totalAttempts})";
    }

    Texture2D ResizeTexture(Texture2D source, int newWidth, int newHeight)
    {
        RenderTexture rt = RenderTexture.GetTemporary(newWidth, newHeight);
        RenderTexture.active = rt;

        Graphics.Blit(source, rt);

        Texture2D result = new Texture2D(newWidth, newHeight);
        result.ReadPixels(new Rect(0, 0, newWidth, newHeight), 0, 0);
        result.Apply();

        RenderTexture.active = null;
        RenderTexture.ReleaseTemporary(rt);

        return result;
    }

    void OnDisable()
    {
        if (worker != null) worker.Dispose();
    }
}