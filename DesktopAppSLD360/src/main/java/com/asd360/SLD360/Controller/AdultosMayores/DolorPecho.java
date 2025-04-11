package com.asd360.SLD360.AdultosMayores;

import com.asd360.SLD360.BaseController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

public class DolorPecho extends BaseController {

    @FXML
    private VBox chatBox;
    @FXML
    private TextField chatInput;
    @FXML
    private ScrollPane chatScrollPane;

    private final String[][] respuestas = {
            {"tengo dolor de pecho", "El dolor en el pecho puede ser un síntoma grave. ¿Es opresivo, punzante o ardoroso? ¿Se irradia a otro lugar?"},
            {"opresivo", "El dolor opresivo en el pecho puede estar relacionado con el corazón. Si es fuerte y persistente, busca atención médica. Mientras tanto, intenta descansar, evitar el estrés y respirar profundamente."},
            {"punzante", "El dolor punzante en el pecho podría deberse a un problema muscular o nervioso. ¿Aumenta con el movimiento? Aplica una compresa tibia y masajea la zona suavemente."},
            {"ardoroso", "El dolor ardoroso en el pecho puede estar relacionado con el reflujo gástrico. ¿Sientes acidez después de comer? Evita alimentos irritantes y no te acuestes inmediatamente después de comer."},
            {"en el pecho", "El dolor en el pecho puede tener diversas causas. Para aliviarlo, intenta relajarte, respirar profundamente y evitar movimientos bruscos. Si persiste o se intensifica, consulta a un médico."},
            {"comenzó hace poco", "Si el dolor es reciente y leve, observa si persiste. Prueba con respiración profunda y descanso. Si se intensifica, consulta a un médico."},
            {"es persistente", "Si el dolor no desaparece, puede ser algo serio. Evita el esfuerzo físico y mantente en reposo hasta que puedas recibir atención médica."},
            {"ansiedad", "El dolor en el pecho a veces puede estar relacionado con la ansiedad. ¿Sientes dificultad para respirar o palpitaciones? Intenta técnicas de relajación como la respiración diafragmática o escuchar música tranquila."},
            {"sí, dificultad para respirar", "La ansiedad puede causar esa sensación. Intenta respirar profundamente y relajarte. Puedes probar la técnica de respiración 4-7-8 (inhalar en 4 segundos, sostener 7 segundos y exhalar en 8 segundos)."},
            {"sí, palpitaciones", "Las palpitaciones junto con dolor en el pecho pueden ser un síntoma importante. Intenta reducir la cafeína, hidratarte bien y descansar. Si persisten, consulta a un médico."},
            {"medicamento", "Recuerda tomar tu medicamento según las indicaciones médicas. ¿Es para el corazón o algún otro tratamiento?"},
            {"para el corazón", "Sigue las indicaciones de tu médico y no omitas dosis. Mantén un estilo de vida saludable con dieta balanceada y ejercicio moderado."},
            {"para otra cosa", "Si tienes dudas sobre tu medicamento, revisa las instrucciones o consulta a un médico. Mantén un registro de efectos secundarios."},
            {"salud mental", "La salud mental es fundamental. Si sientes ansiedad o estrés frecuente, considera buscar ayuda profesional. También puedes probar ejercicios de mindfulness o meditación."},
            {"necesito ayuda", "Hablar con un profesional puede ser muy beneficioso. ¿Quieres que te sugiera algunas opciones de apoyo?"},
            {"dolor", "¿En qué parte tienes dolor? Aplica calor o frío según la causa y descansa la zona afectada."},
            {"leve", "Si el dolor es leve, observa si persiste o cambia en intensidad. Puedes intentar relajarte y aplicar una compresa tibia."},
            {"intenso", "Si el dolor es intenso, es importante buscar atención médica de inmediato. Mientras tanto, descansa y evita hacer esfuerzos."},
            {"me duele el pecho", "Entiendo, ¿el dolor es punzante, opresivo o ardoroso? ¿Se siente en un lado o en el centro?"},
            {"en un lado", "El dolor en un solo lado del pecho puede ser muscular o nervioso. ¿Empeora con el movimiento? Aplica calor en la zona afectada y evita movimientos bruscos."},
            {"en el centro", "El dolor en el centro del pecho puede estar relacionado con el corazón. Si es fuerte o persistente, busca ayuda médica. Mientras tanto, evita el esfuerzo físico y mantén la calma."},
            {"dolor fuerte en el pecho", "Si el dolor es intenso y no cede, busca atención médica urgente. Mientras esperas, siéntate en un lugar cómodo y trata de respirar profundo."},
            {"dolor leve en el pecho", "El dolor leve en el pecho puede tener muchas causas. ¿Ocurre en reposo o al hacer esfuerzo? Intenta relajarte y evitar comidas irritantes."},
            {"dolor en el pecho al acostarme", "Si el dolor aumenta al acostarte, podría ser reflujo ácido o un problema cardíaco. Eleva la cabecera de la cama y evita comidas pesadas antes de dormir."},
            {"sí, acidez", "El reflujo ácido puede causar molestias en el pecho. Evita comidas irritantes y consulta a un médico si persiste."},
            {"sí, tos", "Si el dolor en el pecho se acompaña de tos, podría ser una infección o un problema pulmonar. Mantente hidratado y observa si aparecen otros síntomas."},
            {"dolor en el pecho después de comer", "Podría estar relacionado con acidez o problemas digestivos. Mastica bien los alimentos y evita comer en exceso."},
            {"sí, ardor", "El ardor puede ser causado por reflujo gástrico. Evita comidas irritantes y consulta a un médico si persiste."},
            {"dolor en el pecho por estrés", "El estrés y la ansiedad pueden causar molestias en el pecho. Intenta técnicas de respiración y ejercicios de relajación."},
            {"sí, varias veces", "Si ocurre frecuentemente, podrías beneficiarte de técnicas de relajación o apoyo profesional."},
            {"dolor en el pecho y mareo", "Si sientes dolor en el pecho junto con mareo, podría ser un problema de presión arterial. Siéntate, respira profundo y evita cambios bruscos de posición."},
            {"sí, hipertensión", "Si tienes hipertensión y sientes estos síntomas, consulta a un médico de inmediato. Evita la sal y mantente bien hidratado."},
            {"dolor en el pecho y falta de aire", "La falta de aire junto con el dolor en el pecho puede ser un síntoma grave. ¿Desde cuándo lo sientes?"},
            {"hace poco", "Si es reciente pero intenso, busca atención médica. Mientras tanto, intenta mantener la calma y respirar profundamente."},
            {"desde hace tiempo", "Si el problema es persistente, es importante hacer una evaluación médica."},
            {"dolor en el pecho y sudoración", "Si sientes dolor en el pecho acompañado de sudoración, consulta a un médico de inmediato. Mientras tanto, siéntate y trata de relajarte."},
            {"presión en el pecho", "Una sensación de presión en el pecho puede estar relacionada con el corazón. ¿Es persistente o intermitente? Siéntate y respira profundo para ver si mejora."},
            {"persistente", "Si la presión es persistente, busca atención médica. Mientras tanto, evita esfuerzos y mantente hidratado."},
            {"intermitente", "Si va y viene, observa si se relaciona con actividad física o estrés. Prueba ejercicios de respiración y descanso."},
            {"punzada en el pecho", "Si sientes punzadas en el pecho, podría ser un problema muscular, nervioso o cardíaco. ¿Ocurre al moverte o en reposo?"},
            {"al moverte", "Si el dolor aparece con el movimiento, podría ser muscular. Aplica calor en la zona y descansa."},
            {"en reposo", "Si ocurre en reposo, es importante observar si se intensifica. Intenta relajarte y evitar la cafeína."},
            {"default", "No tengo una respuesta específica para eso, pero dime más detalles y trataré de ayudarte."}
    };

    @FXML
    private void handleSendMessage(ActionEvent event) {
        String mensaje = chatInput.getText().trim().toLowerCase();
        if (mensaje.isEmpty()) return;

        agregarMensaje("Tú: " + mensaje, "usuario");

        String respuesta = obtenerRespuesta(mensaje);

        agregarMensaje("Asistente: " + respuesta, "bot");

        chatInput.clear();
    }

    private String obtenerRespuesta(String mensaje) {
        for (String[] par : respuestas) {
            if (mensaje.contains(par[0])) {
                return par[1];
            }
        }
        return respuestas[respuestas.length - 1][1];
    }

    private void agregarMensaje(String mensaje, String tipo) {
        Label mensajeLabel = new Label(mensaje);
        mensajeLabel.setWrapText(true);
        mensajeLabel.setStyle("-fx-background-color: " + (tipo.equals("usuario") ? "#d1e7dd" : "#ffeeba") + "; -fx-padding: 5; -fx-background-radius: 5;");
        chatBox.getChildren().add(mensajeLabel);

        chatScrollPane.setVvalue(1.0);
    }
}
