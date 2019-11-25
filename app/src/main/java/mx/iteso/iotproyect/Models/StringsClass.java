package mx.iteso.iotproyect.Models;

//TODO(Clase) Se declara todos los mensajes o strings requeridos en la aplicación.
public class StringsClass {

    /** Url **/
    //public static final String Url = "http://tuppersens.us-west-2.elasticbeanstalk.com/api/servicesIoT/";
    //private static final String localhost="148.201.214.14";
    private static final String localhost="192.168.1.6";
    public static final String Url = "http://"+localhost+"/IoT/api/servicesiot/";

    /** Declaraciones **/
    public  static final  String emailFormat = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
    public static final String idField ="id";
    public static final String nameField ="name";
    public static final String userIdField ="userId";
    public static final String levelField ="level";
    public static final String typeField ="type";
    public static final String dbRealmName = "iotTeam3.realm";
    public static final String titleFloatingButton ="Agregar topper";
    public static final String MessageFloatingButton ="¿Desea agregar un nuevo topper?";
    public static final String titleElemetList ="Editar topper";
    public static final String MessageElemetListPart1 ="¿Desea editar el topper '";
    public static final String MessageElemetListPart2 ="'?";

    public static final String MessageHitTextField1Case0="Ingresa tu correo";


    public static final String MessageHitTextField2Case0="Ingresa tu nombre";
    public static final String MessageHitTextField2Case1="Ingrese el nombre del topper";

    public static final String titleButtonSaveCase0="Guardar datos";
    public static final String titleButtonSaveCase1="Guardar Topper";
    public static final String titleButtonSaveCase2="Guardar Nombre";

    /** Alertas & Mensajes **/
    public static final String MessageTagError = "Error: ";
    public static final  String MessageLoadingInfo ="Cargar...";
    public static final String MessageQrScan = "Coloque un código Qr en el interior del rectángulo del visor para escanear";
    public static final String MessageQrScanError ="Error al capturar el código QR";
    public static final String MessageGetDataError ="Error al capturar los datos";
    public static final String MessageTopperIsNotNew ="El topper ya fue registrado";
    public static final String MessageEmailError ="El correo no es valido";
    public static final String MessageFieldEmpty="Agrege nombre o correo, campo(s) vacios";

    /** Notificaciones **/
    public static final String CHANNEL_ID ="1";
    public static final String CHANNEL_NAME ="CHANNEL TRANSMITION";

    /** Mensajes en consola**/
    public static String FCMTagTitleIDService ="Firebase ID Service: ";
    public static String FCMTagTokenIDService ="Refreshed token: ";

    public static String FCMTagTitleMessage = "Firebase Messages: ";
    public static String FCMTagFromMessage = "From: ";
    public static String FCMTagMessage = "Message data payload: ";
}
