package com.fisi.proyectocursos.dto.controller;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fisi.proyectocursos.dto.RecoveryDTO;
import com.fisi.proyectocursos.model.User;
import com.fisi.proyectocursos.service.impl.UserServiceImpl;
import com.fisi.proyectocursos.utils.RecoveryURL;

import net.bytebuddy.utility.RandomString;
//se creo un nuevo controlador para el cambio de contraseña
@Controller
public class ForgotPasswordController {

    //se inyecta el servicio de usuario
    @Autowired
    private UserServiceImpl userService;

    //se inyecta el servicio de envio de correo
    @Autowired
    private JavaMailSender mailSender;

    //se crea un token para el cambio de contraseña
    private String token;

    //se crea un metodo para recuperar la contraseña
    @GetMapping("/recuperar")
    public String recuperar(@ModelAttribute("recovery") RecoveryDTO recovery) {
        return "auth/recovery";
    }

    //se crea un metodo POST para enviar el correo de recuperacion de contraseña
    @PostMapping("/recuperar")
    public String recuperar(@ModelAttribute("recovery") RecoveryDTO recovery, Model model,
                            RedirectAttributes redirectAttributes, HttpServletRequest request) {
        //se verifica si el correo existe
        if (userService.existsByUsername(recovery.getEmail())) {
            String token = RandomString.make(45);
            //se actualiza el token
            userService.updateResetPasswordToken(token, recovery.getEmail());
            //se crea el link para restablecer la contraseña
            String resetPasswordLink = RecoveryURL.getSiteURL(request) + "/restablecer_contrasena?token=" + token;
            //se envia el correo
            try {
                sendEmail(recovery.getEmail(), resetPasswordLink);
            } catch (UnsupportedEncodingException | MessagingException e) {
                redirectAttributes.addFlashAttribute("notification", "Error al enviar el correo.");
                //se captura el error
            }
            //se envia un mensaje de exito
            redirectAttributes.addFlashAttribute("notification",
                    "Te acabamos de enviar un correo con las instrucciones para restablecer tu contraseña.");
            return "redirect:/login";
        } else {
            //se envia un mensaje de error
            model.addAttribute("msg", "El correo ingresado no está registrado");
            return "auth/recovery";
        }

    }



    //se crea un metodo GET para restablecer la contraseña
    @GetMapping("/restablecer_contrasena")
    public String restablecer(@RequestParam String token, @ModelAttribute("recovery") RecoveryDTO recovery, Model model) {
        this.token = token;
        return "auth/change_password";
    }

    @PostMapping("/restablecer_contrasena")
    public String restablecer(@ModelAttribute("recovery") RecoveryDTO recovery, Model model, RedirectAttributes redirectAttributes) {
//        MINIMIZACION DEL USO DE LA CLASE USERSERVICEIMPL EN EL CONTROLADOR
//        EJEMPLO DE CODE SMELL: FEATURE ENVY
        Boolean response = userService.resetPassword(this.token, recovery.getNewPassword());
        if (response) {
            redirectAttributes.addFlashAttribute("notification", "Tu contraseña se ha restablecido con éxito.");
            return "redirect:/login";
        } else {
            model.addAttribute("msg", "El token es inválido.");
            return "auth/change_password";
        }

    }

    private void sendEmail(String email, String resetPasswordLink) throws UnsupportedEncodingException, MessagingException {
        //se crea el mensaje
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        //Se configura el mensaje con el correo de soporte como son el destinatario, el saunto y el contenido del correo
        helper.setFrom("contacto@edufind.com", "Soporte EDUFIND");
        helper.setTo(email);

        String subject = "EDUFIND - Restablece tu contraseña";

        String content = "<p>Hola,</p>"
                + "<p>Recibimos tu solicitud de cambio de contraseña.</p>"
                + "<p>Para restablecer tu contraseña usa el siguiente enlace:</p>"
                + "<a href=\"" + resetPasswordLink + "\">Cambiar mi contraseña</a>"
                + "<p>Si no has solicitado restablecer tu contraseña, comunícanoslo respondiendo directamente a este correo electrónico. Aún no se han hecho cambios en tu cuenta.</p>";

        helper.setSubject(subject);
        helper.setText(content, true);
        //se envia el correo
        mailSender.send(message);


    }

}
