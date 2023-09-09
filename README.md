## Pastefy Java APIClient

```java
import de.interaapps.pastefy.apiclient.exceptions.CreationFailedException;
import de.interaapps.pastefy.apiclient.exceptions.PasswordIncorrectException;

class Test {
    public static void main(String[] args) {
        PastefyAPI pastefyAPI = new PastefyAPI("API-Key");

        /// Paste

        // GETTING A PASTE
        Paste aCoolPaste = pastefyAPI.getPaste("6RfWSb9P");
        if (aCoolPaste != null) {
            System.out.println(aCoolPaste.getContent());


            if (aCoolPaste.isEncrypted()) {
                try {
                    aCoolPaste.decrypt("PASSWORD");
                } catch (PasswordIncorrectException e) {
                    System.out.println("error");
                }
            }
        }
        // Decrypting encrypted pastes

        // Creating a paste
        Paste paste = new Paste();
        paste.setTitle("A nice paste");
        paste.setContent("Hello there!");

        try {
            pastefyAPI.createPaste(paste);
            System.out.println("There is a new paste!: " + paste.getId());

            // Share paste to friend
            if (pastefyAPI.addFriendToPaste(paste, "HomerSimpson")) {
                System.out.println("AMAZING!");
            }
        } catch (CreationFailedException e) {
            System.out.println("Doh!");
        }

        // Deleting a paste
        pastefyAPI.deletePaste(paste);
        // or
        pastefyAPI.deletePaste("ID");

        /// Folder
        // Important: Use Folder
        Folder folder = pastefyAPI.getFolder("abcdefgh");

        if (folder != null) {
            System.out.println(folder.getName());

            // Go trough the folder
            folder.getPastes().forEach(paste -> {
                System.out.println("There is a paste called " + paste.getTitle());
            });

            // Children folder
            folder.getChildren().forEach(folder -> {
                System.out.println("There is a sub-folder called " + folder.getName());
            });
        }

        // Create Folder 
        Folder newFolder = new Folder();
        newFolder.setName("Yay");
        pastefyAPI.createFolder(newFolder);
        System.out.println("A new folder appeared: " + newFolder.getId());

        // Deleting a folder
        pastefyAPI.deleteFolder(folder);
    }
}
```

## Installation
```xml
	<repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>

	<dependency>
	    <groupId>com.github.interaapps</groupId>
	    <artifactId>pastefy-java-apiclient</artifactId>
	    <version>1.0 <!-- TAG --></version>
	</dependency>
```
