package Libraries;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Pack {
	public void doPack(Path source, Path target) throws IOException {
		try (FileOutputStream fos = new FileOutputStream(target.toFile());
				ZipOutputStream zos = new ZipOutputStream(fos)) {
			Files.walkFileTree(source, new SimpleFileVisitor<Path>() {
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
					zos.putNextEntry(new ZipEntry(source.relativize(file).toString()));
					Files.copy(file, zos);
					zos.closeEntry();
					return FileVisitResult.CONTINUE;
				}
			});
		}

	}
	
	public static void main(String[]args) throws IOException{
		Pack pack=new Pack();
		pack.doPack(new File("Diagrams/HelloWorld").toPath(), new File("HelloWorld.uml").toPath());
	}
	
	
}
