# Kindle Clippings Organizer
This program analyses the `My Clippings.txt` file generated by a Kindle e-reader and divides all your highlights and notes by book.
The result is stored in a JSON file, which can be placed in any folder and re-opened by the application to see your past clippings and add new ones (from a new or updated `My Clippings.txt` file).
>The `My Clippings.txt` file can be found in the device's internal memory inside the `documents` folder.
# Compilation
The project is compiled through Maven. Run:
```bash
mvn clean package
```

You can also download the Jar directly from the "Releases" section on GitHub.
# Usage
Run `KCO-jar-with-dependencies.jar`, either by double clicking (if supported by OS) or inside a terminal:
```bash
java -jar KCO-jar-with-dependencies.jar
```
Inside the application, use the provided buttons to select the `My Clippings.txt` file and the folder where the `Clippings.json` file will be created.  
If no txt file is provided, the application will just look for the `Clippings.json` in the provided folder, and will create an empty one if nothing is found.  
After loading or creating a JSON file, a list of books will show up. Double click on an entry to see all clippings from that book. From there, text can be easily selected and copied.
