## Pastefy Java APIClient

```java
class Test {
    public static void main(String[] args) {
        PastefyAPI pastefyAPI = new PastefyAPI("API-Key");

        /// Paste

        // GETTING A PASTE
        Paste aCoolPaste = pastefyAPI.getPaste("6RfWSb9P");
        if (aCoolPaste.exists()) {
            System.out.println(aCoolPaste.getContent());
        }
        // Decrypting encrypted pastes
        if (aCoolPaste.isEncrypted()) {
            if (aCoolPaste.decrypt("PASSWORD")) {
                System.out.println(aCoolPaste.getContent());
            }
        }

        // Creating a paste
        Paste paste = new Paste(pastefyAPI);
        paste.setTitle("A nice paste");
        paste.setContent("Hello there!");

        if (paste.create()) {
            System.out.println("There is a new paste!: " + paste.getId());

            // Share paste to friend
            if (paste.addFriend("HomerSimpson")) {
                System.out.println("AMAZING!");
            }
        } else {
            System.out.println("Doh!");
        }

        // Deleting a paste
        paste.delete();
        // or
        pastefyAPI.deletePaste("ID");

        /// Folder
        // Important: Use Folder
        Folder folder = pastefyAPI.getFolder("abcdefgh");

        if (folder.exists()) {
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
        Folder newFolder = new Folder(pastefyAPI);
        newFolder.setName("Yay");
        newFolder.create();
        System.out.println("A new folder appeared: " + newFolder.getId());

        // Deleting a folder
        newFolder.delete();
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
