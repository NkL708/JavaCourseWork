package classes;

import java.io.*;

public record User(String username) implements Serializable
{
    private static final File file = new File("src/main/resources/saveFiles/user.dat");

    public String getUsername()
    {
        return username;
    }

    public void saveUserToFile() throws IOException
    {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file.getPath())))
        {
            objectOutputStream.writeObject(this);
        }
    }

    public static User readUserFromFile() throws IOException, ClassNotFoundException
    {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file.getPath())))
        {
            return (User) objectInputStream.readObject();
        }
    }
}