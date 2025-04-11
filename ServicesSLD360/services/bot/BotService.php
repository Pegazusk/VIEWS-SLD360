<?php

class BotService extends Service
{
    function consultar()
    {
        $mensaje = $_POST['mensaje'];
        $api_key = 'AIzaSyAIxk5jGAB_U3c4IFMgDzkHodQZAX88cPI';

        $url = 'https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=' . $api_key;

        $data = [
            'contents' => [
                [
                    'parts' => [
                        ['text' => 'Eres un asistente médico profesional. Responde de manera clara y concisa.'],
                        ['text' => $mensaje]
                    ]
                ]
            ],
            'safetySettings' => [
                [
                    'category' => 'HARM_CATEGORY_DANGEROUS_CONTENT',
                    'threshold' => 'BLOCK_NONE'
                ]
            ]
        ];

        $options = [
            'http' => [
                'header' => "Content-Type: application/json\r\n",
                'method' => 'POST',
                'content' => json_encode($data),
                'ignore_errors' => true // Para manejar errores HTTP
            ]
        ];

        $context = stream_context_create($options);
        $result = file_get_contents($url, false, $context);

        // Manejo mejorado de errores
        if ($result === FALSE) {
            $error = error_get_last();
            return [
                'Error' => true,
                'ErrorMensaje' => 'Error al conectar con el servicio: ' . ($error['message'] ?? 'Desconocido')
            ];
        }

        $response = json_decode($result, true);

        if (isset($response['candidates'][0]['content']['parts'][0]['text'])) {
            return ['Error' => false, 'ErrorMensaje' => '', 'Mensaje' => $response['candidates'][0]['content']['parts'][0]['text']];
        } else {
            // Debug: Registrar la respuesta completa para diagnóstico
            error_log("Respuesta de la API: " . print_r($response, true));
            return [
                'Error' => true,
                'ErrorMensaje' => 'La API no devolvió una respuesta válida',
                'Response' => $response // Opcional: para depuración
            ];
        }
    }
}