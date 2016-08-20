package Libraries;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
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

	// Source = zip file
	public void doUnPack(String source, String target) {
		File folder = new File("Diagrams" + File.separator + target);
		folder.mkdirs();
		File output = new File("Diagrams" + File.separator + target);
		byte[] buffer = new byte[2048];
		try {
			FileInputStream input = new FileInputStream(source);
			ZipInputStream zip = new ZipInputStream(input);
			ZipEntry entry = zip.getNextEntry();
			while (entry != null) {
				String fileName = entry.getName();
				File file = new File(output + File.separator + fileName);
				FileOutputStream foutput = new FileOutputStream(file);
				int count = 0;
				while ((count = zip.read(buffer)) > 0) {
					foutput.write(buffer, 0, count);

				}
				foutput.close();
				zip.closeEntry();
				entry = zip.getNextEntry();
			}
			zip.closeEntry();
			input.close();

		} catch (IOException e) {

			e.printStackTrace();

		}

	}
}
