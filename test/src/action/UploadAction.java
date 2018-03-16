package action;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

public class UploadAction {
	private File image;
	private String imageFileName;

	public String getImageFileName() {
		return imageFileName;
	}

	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}

	// private String imageContentType;
	public String up() throws IOException {
		String realPath = ServletActionContext.getServletContext().getRealPath("/images");
		System.out.println("realPath=" + realPath);
		File saveFile = new File(realPath, imageFileName);
		System.out.println(imageFileName);
		if (!saveFile.exists())
			saveFile.getParentFile().mkdirs();
		FileUtils.copyFile(image, saveFile);
		return "OK";
	}

	public File getImage() {
		return image;
	}

	public void setImage(File image) {
		this.image = image;
	}
}
