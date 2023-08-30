package backend;


import org.example.files.FileReader;
import org.example.files.exception.ExceptionFile;
import org.example.files.filesFactory.FilesFactoryImplementation;
import org.example.files.filesFactory.FilesFactoryInterface;
import org.junit.Before;
import org.junit.Test;
import static junit.framework.Assert.assertEquals;
public class ReadFileTest {

    FilesFactoryInterface filesFactory;

    @Before
    public void init() {
        filesFactory = new FilesFactoryImplementation();
    }
    @Test
    public void readFileImageExistExtension() throws Exception {
        //Arrange
        //Act
        FileReader fileReaderFileImage = filesFactory.getInstance("fondo.jpg");
        //Assert
        assertEquals("image/jpg", fileReaderFileImage.getContent_types());
    }

    @Test(expected = ExceptionFile.class)
    public void readFileImageNotExistExtension() throws ExceptionFile {
        //Arrange
        //Act
        FileReader fileReaderFileImage = filesFactory.getInstance("no");
        //Assert
    }

    @Test
    public void readFileTextExistExtension() throws Exception {
        //Arrange
        //Act
        FileReader fileReaderFileImage = filesFactory.getInstance("fondo.html");
        //Assert
        assertEquals("text/html", fileReaderFileImage.getContent_types());
    }

    @Test(expected = ExceptionFile.class)
    public void readFileTextNotExistExtension() throws ExceptionFile {
        //Arrange
        //Act
        FileReader fileReaderFileImage = filesFactory.getInstance("no");
        //Assert
    }
}
