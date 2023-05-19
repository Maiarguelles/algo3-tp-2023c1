public interface Effect extends Adaptable {
    public typeOfEffect produceEffect();

    public enum typeOfEffect{
        NOTIFICATION,
        EMAIL,
        SOUND,
    }

}
//hola