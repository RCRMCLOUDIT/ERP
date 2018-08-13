package com.cap.util.imageclip;

import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.applet.*;
import java.awt.datatransfer.*;
import java.awt.datatransfer.DataFlavor;
import java.io.*;
import java.net.*;
import javax.swing.*;
import java.beans.*;


public class ClipApplet extends Applet
{
	//Parameters
	public String servletURL = null;
	public int maxImageSize = 0;
	public String imageId = null;
	public int colors = 255;
	public String creator = null;
	public String template = null;
	public String downloadJRElnk = null;
	public String libname = null;
	public String responseString = null;
	public boolean imageFromClipboard = false;
	public boolean imageFromUpload = false;

	/**UI Objects**/

	//Standard copy/paste
	//public JPanel mainPanel = null;
	public JPanel topPanel = null;
	public JPanel bottomPanel = null;
	
	public Clipboard clipboard = null;
	public Image defaultImage = null;
	public JButton cmdPaste = null;
	public JButton cmdSend = null;
	public JLabel lblStatus = null;
	public ImageViewer imgViewer = null;
	public JLabel wrongVersionLabel = null;
	public JLabel linkLabel = null;
	
	//FileUpload
	public JButton showFileChooser;
	public boolean addFilters;
	public FileFilterer jpgFilter, gifFilter, bothFilter;
	public FileViewer fileView;
	public FilePreviewer previewer;
	public JFileChooser chooser;
	public JFrame fileChooserPanel;
	public Image tmpImage;
	public String fileLnk;
	static String lnfClassName = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
	static final Color bgColor = new Color(255,255,255);
	static final Color bgColor1 = new Color(128, 128, 192);

	//NLS tags
	String button1 = "";
	String button2 = "";
	String button3 = "";
	String choseFile = "";
	String nothingChosen = "";
	String encodingMessage = "";
	String sending = "";
	String cantConnect = "";
	String sendImage = "";
	String pasteImage = "";
	String tooLarge = "";
	String imageSent = "";
	String wrongVersion = "";
	String dlLink = "";
	
	/** INNER CLASSES **/

	class FileChooser extends JPanel implements ActionListener
	{
	    JButton openButton;

	    public FileChooser()
	    {
	        chooser = new JFileChooser();
        	previewer = new FilePreviewer(chooser);
   			chooser.addChoosableFileFilter(bothFilter);
			chooser.addChoosableFileFilter(jpgFilter);
			chooser.addChoosableFileFilter(gifFilter);
			chooser.setDialogType(JFileChooser.OPEN_DIALOG);
			chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			chooser.setAccessory(previewer);
			chooser.setFileView(fileView);
		}

	    public void actionPerformed(ActionEvent e)
		{
			int retval = chooser.showDialog(fileChooserPanel, null);
			if(retval == JFileChooser.APPROVE_OPTION)
			{
			    File theFile = chooser.getSelectedFile();
			    if(theFile != null)
			    {
				    JOptionPane.showMessageDialog(fileChooserPanel, choseFile  + chooser.getSelectedFile().getAbsolutePath());
				    fileLnk = chooser.getSelectedFile().getAbsolutePath();

				    tmpImage = Toolkit.getDefaultToolkit().getImage(chooser.getSelectedFile().getAbsolutePath());
			        try
			        {
			            MediaTracker tracker = new MediaTracker(this);
			            tracker.addImage(tmpImage, 0);
			            tracker.waitForID(0);
						imageFromUpload = true;
				    }
			        catch (Exception exx)
			        {
				    }
					//resizeCurrentImage(tmpImage, template);
				    imgViewer.setImage(tmpImage);
					return;
			    }
			}
			JOptionPane.showMessageDialog(fileChooserPanel, nothingChosen);
	    }

    // overrides imageUpdate to control the animated gif's animation
    public boolean imageUpdate(Image img, int infoflags,
                int x, int y, int width, int height)
    {
        if (isShowing() && (infoflags & ALLBITS) != 0)
            repaint();
        if (isShowing() && (infoflags & FRAMEBITS) != 0)
            repaint();
        return isShowing();
    }

	}

	//Preview image in Filechooser panel
	class FilePreviewer extends JComponent implements PropertyChangeListener
	{
		ImageIcon thumbnail = null;
		File f = null;

		public FilePreviewer(JFileChooser fc)
		{
		    setPreferredSize(new Dimension(100, 100));
		    fc.addPropertyChangeListener(this);
		}

		public void loadImage()
		{
		    if(f != null)
		    {
				ImageIcon tmpIcon = new ImageIcon(f.getPath());
				if(tmpIcon.getIconWidth() > 90)
				{
				    thumbnail = new ImageIcon(tmpIcon.getImage().getScaledInstance(90, -1, Image.SCALE_DEFAULT));
				}
				else
				{
				    thumbnail = tmpIcon;
				}
		    }
		}

		public void propertyChange(PropertyChangeEvent e)
		{
		    String prop = e.getPropertyName();
		    if(prop == JFileChooser.SELECTED_FILE_CHANGED_PROPERTY)
		    {
				f = (File) e.getNewValue();
				if(isShowing())
				{
					loadImage();
				    repaint();
				}
		    }
		}

		public void paint(Graphics g)
		{
		    if(thumbnail == null)
		    {
				loadImage();
		    }
		    if(thumbnail != null)
		    {
				int x = getWidth()/2 - thumbnail.getIconWidth()/2;
				int y = getHeight()/2 - thumbnail.getIconHeight()/2;
				if(y < 0)
				{
				    y = 0;
				}

				if(x < 5)
				{
				    x = 5;
				}
				thumbnail.paintIcon(this, g, x, y);
	 	   }
		}
	} //end of inner class FilePreviewer


	/**  ACTION LISTENERS**/

	class cmdPasteActionListener implements ActionListener
	{
		public void actionPerformed (ActionEvent event)
		{
			//get clipboard object
			Transferable clipboardContent = clipboard.getContents(this);

			if ((clipboardContent != null) && (clipboardContent.isDataFlavorSupported(DataFlavor.imageFlavor)))
			{
				try
				{
					Image image = (Image) clipboardContent.getTransferData(DataFlavor.imageFlavor);
					resizeCurrentImage(image, template);
					imageFromClipboard = true;
				}
				catch (Exception e)
				{
					setStatusText(e.getMessage());
					e.printStackTrace();
				}
			}
			else
			{
				setStatusText(pasteImage);
				actionClearImage();
			}
		}//end of method actionPerformed()
	}//end of inner class action listener cmdPasteActionListener

	class cmdSendActionListener implements ActionListener
	{
		public void actionPerformed (ActionEvent event)
		{
			actionSendImage();
			//writeFile();
		}

	}//end of inner class action listener cmdSendActionListener


	//used to show default image
	public void actionClearImage()
	{
		this.imgViewer.setImage(this.defaultImage);
		repaint();
	}
	private void actionPasteImage(Image image)
	{
		//reducer number of colors in copied image to 255 so that the gif encoder can propery encode the image
		try
		{
			this.imgViewer.setImage(image);
			int pixels[][] = getPixels(imgViewer.getImage());
			int palette[] = Quantize.quantizeImage(pixels, colors);
			imgViewer.setImage(palette, pixels);
			repaint();
			setStatusText(sendImage);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	public void actionSendImage()
	{
		try
		{
			setStatusText(encodingMessage);

			if(template.equals("PDF")) {
				imageFromClipboard = true;
			}

			GIFEncoder encoder = null;
			ByteArrayOutputStream gif = new ByteArrayOutputStream();
			int height;
			int width;
			int size = 0;
			byte[] tmp = null;

			if(imageFromClipboard == true)
			{
				System.out.println("Getting image from imgviewer");
				encoder = new GIFEncoder(imgViewer.getImage());
				encoder.Write(gif);
			}
			else if(imageFromUpload == true)
			{
				System.out.println("Getting image from tmpimage");
				tmp = toBytes(fileLnk);
				gif.write(tmp);
    			File f = new File(fileLnk);
   				FileInputStream fis = new FileInputStream(f);
			    size = (int)f.length();
			    tmp = new byte[size];
   				fis.read(tmp);
			    fis.close();

			}
			
			
			if(imageFromUpload == false && imageFromClipboard == false)
			{
				setStatusText("Please select an image to upload.");	
			}
			

			else if ((gif.size() > this.maxImageSize )|| (size >  this.maxImageSize))
			{
				setStatusText(tooLarge);
			}
			else
			{
				setStatusText(sending);
				HttpURLConnection conn = null;
				try
				{
					conn = (HttpURLConnection) (new URL(this.servletURL)).openConnection();
				}
				catch(Exception e)
				{
					System.out.println(cantConnect);
					System.out.println(e.toString());
					setStatusText(cantConnect);
					e.printStackTrace();
				}
				conn.setRequestMethod("POST");
				conn.setDoOutput(true);
				conn.setDoInput(true);

				conn.connect();

				OutputStream output = conn.getOutputStream();

				//write out info
				writePaddedString(output, this.imageId, 5);
				writePaddedString(output, "image/gif", 30);
				writePaddedString(output, Integer.toString(gif.size()), 10);
				writePaddedString(output, libname, 2);

				if(imageFromClipboard == true)
				{
					int n = 0;
					int data;
					byte[] buffer = new byte[512];
					InputStream is = new ByteArrayInputStream(gif.toByteArray(), 0, gif.size());

					//write out image
					while ( (data = is.read()) != -1 )
					{
						output.write(data);
						n++;
					}

				}
				else
				{
					int n = 0;
					int data;
					byte[] buffer = new byte[512];
					InputStream is = new ByteArrayInputStream(tmp, 0, size);

					//write out image
					while ( (data = is.read()) != -1 )
					{
						output.write(data);
						n++;
					}

				}

				readServletResponse(conn);
				System.out.println("Got Response");
				System.out.println(responseString);


				//close connection
				output.flush();
				output.close();
				conn.disconnect();

				setStatusText(imageSent);

				//redirect to autoclose page
				getAppletContext().showDocument(new URL(responseString), "_self");

				}//end of else

			}//end of try
			catch (Exception e)
			{
				setStatusText(e.getMessage());
				e.printStackTrace();
			}

		}//end of method actionSendImage()


	//used in resizing and scaling of image
	static int[][] getPixels(Image image) throws IOException
	{
		int w = image.getWidth(null);
	    int h = image.getHeight(null);
	    int pix[] = new int[w * h];
	    PixelGrabber grabber = new PixelGrabber(image, 0, 0, w, h, pix, 0, w);

	    try
	    {
	    	if (grabber.grabPixels() != true)
	        {
	     	   throw new IOException("Grabber returned false: " + grabber.status());
	        }
	    }
	    catch (InterruptedException e)
	    {
	        e.printStackTrace();
	    }

	    int pixels[][] = new int[w][h];
	    for (int x = w; x-- > 0; )
	    {
	    	for (int y = h; y-- > 0; )
	        {
	     	   pixels[x][y] = pix[y * w + x];
	        }
	    }

        return pixels;
    }
	public void init()
	{
		try
		{
			UIManager.setLookAndFeel(lnfClassName);
		}
		catch (Exception e)
		{
		   	System.err.println("Error loading Look and Feel class: " + e);
		}

		topPanel = new JPanel();
		topPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		topPanel.setBackground(bgColor);

		bottomPanel = new JPanel();
		bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		bottomPanel.setBackground(bgColor);
		
        //check java version running to see which setup to show
        String version = System.getProperty("java.version");

        //If version is before 1.4...
        Float versionNumber = new Float(version.substring(0,3));

		this.setLayout(new BorderLayout(10, 10));
		
		if(versionNumber.compareTo(new Float("1.4f")) < 0)
		{
			wrongVersionLabel = new JLabel(wrongVersion);
			linkLabel = new JLabel(dlLink);

			wrongVersionLabel.setForeground(new Color(225, 0, 0));
			wrongVersionLabel.setBackground(bgColor);
			wrongVersionLabel.addMouseListener(new MouseAdapter()
			{
				public void mouseClicked(MouseEvent evt)
				{
					Object obj = evt.getSource();
					if (obj == wrongVersionLabel)
					{
						try
						{
							getAppletContext().showDocument(new URL(downloadJRElnk), "_blank");
						}
						catch(MalformedURLException mfe)
						{
							System.out.println("downloadJRElnk is invalid");
						}
					}
				}

				public void mouseEntered(MouseEvent evt)
				{
					Object obj = evt.getSource();

					if (obj == wrongVersionLabel)
					wrongVersionLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				}

				public void mouseExited(MouseEvent evt)
				{
					Object obj = evt.getSource();

					if (obj == wrongVersionLabel)
					wrongVersionLabel.setCursor(Cursor.getDefaultCursor());
				}

				});
				linkLabel.setForeground(new Color(225, 0, 0));
				linkLabel.setBackground(bgColor);
				linkLabel.addMouseListener(new MouseAdapter()
				{
					public void mouseClicked(MouseEvent evt)
					{
						Object obj = evt.getSource();
						if (obj == linkLabel)
						{
							try
							{
								getAppletContext().showDocument(new URL("/"), "_blank");
							}
							catch(MalformedURLException mfe)
							{
								System.out.println("Bad URL!");
							}
						}
					}

					public void mouseEntered(MouseEvent evt)
					{
						Object obj = evt.getSource();

						if (obj == linkLabel)
						linkLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
					}

					public void mouseExited(MouseEvent evt)
					{
						Object obj = evt.getSource();

						if (obj == linkLabel)
						linkLabel.setCursor(Cursor.getDefaultCursor());
					}

				}); //end of action listener

				this.add(wrongVersionLabel, BorderLayout.PAGE_START);
				this.add(linkLabel, BorderLayout.PAGE_END);
				this.setBackground(bgColor);

		}
		//If version is greater or equal to 1.4
		else
		{
			//read parameters in <PARAM> tags
			readParameters();

			//get clipboard object
			clipboard = getToolkit().getSystemClipboard();

			//initialize file filters
			jpgFilter = new FileFilterer("jpg", "JPEG Compressed Image Files");
			gifFilter = new FileFilterer("gif", "GIF Image Files");
			bothFilter = new FileFilterer(new String[] {"jpg", "gif"}, "JPEG and GIF Image Files");
			fileView = new FileViewer();
			//fileView.putIcon("jpg", new ImageIcon(getDocumentBase(), "jpg.jpg"));
			//fileView.putIcon("gif", new ImageIcon(getDocumentBase(), "gif.gif"));
			
			fileView.putIcon("jpg", new ImageIcon(getCodeBase(), "jpg.jpg"));
			fileView.putIcon("gif", new ImageIcon(getCodeBase(), "gif.gif"));
			/** INITIALIZE UI OBJECTS **/

			Font HEAD_FONT = new Font("Helvetica", Font.BOLD, 12);

			//file chooser button and caller
			showFileChooser = new JButton(button1);
			showFileChooser.addActionListener(new FileChooser());
			showFileChooser.setBackground(bgColor1);
			showFileChooser.setForeground(bgColor);
			showFileChooser.setFont(HEAD_FONT);

			//Paste Button
			cmdPaste = new JButton(button2);
			cmdPaste.addActionListener (new cmdPasteActionListener());
			cmdPaste.setBackground(bgColor1);
			cmdPaste.setForeground(bgColor);
			cmdPaste.setFont(HEAD_FONT);
			//Send Image Button
			cmdSend = new JButton(button3);
			cmdSend.addActionListener (new cmdSendActionListener());
			cmdSend.setBackground(bgColor1);
			cmdSend.setForeground(bgColor);
			cmdSend.setFont(HEAD_FONT);
			
			//Status bar
			lblStatus = new JLabel(pasteImage, Label.LEFT);
			lblStatus.setPreferredSize(new Dimension(350,20));
			lblStatus.setBackground(bgColor);

			//Image holder
			imgViewer = new ImageViewer();
			imgViewer.setMaxSize(this.getWidth(),this.getHeight()-70);

			//set default image
			//defaultImage = getImage(getDocumentBase(), "noimage.jpg");
			defaultImage = getImage(getCodeBase(), "noimage.jpg");
			MediaTracker mt = new MediaTracker(this);
			mt.addImage(this.defaultImage, 1);
			try
			{
				mt.waitForAll();
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}

			//Add UI objects to applet canvas
			topPanel.add(this.showFileChooser);
			topPanel.add(this.cmdPaste);
			topPanel.add(this.cmdSend);

			bottomPanel.add(this.lblStatus);

			this.add(topPanel, BorderLayout.NORTH);
			this.add(this.imgViewer, BorderLayout.CENTER);
			this.add(bottomPanel, BorderLayout.SOUTH);
		
			
			//make background white
			this.setBackground(bgColor);

			//make sure default image is shows
			actionClearImage();
		}

	}//end of main
	//Read parameters in <PARAM> tags
	public void readParameters()
	{
		if (this.getParameter("servletURL") != null)
		{
			this.servletURL = this.getParameter("servletURL");
		}
		if (this.getParameter("maxImageSize") != null)
		{
			this.maxImageSize = Integer.parseInt(this.getParameter("maxImageSize"));
		}
		if (this.getParameter("imageId") != null)
		{
			//this.imageId = new BigDecimal(this.getParameter("ImageId"));
			this.imageId = this.getParameter("ImageId");
		}
		if (this.getParameter("colors") != null)
		{
			this.colors = Integer.parseInt(this.getParameter("colors"));
		}
		if (this.getParameter("creator") != null)
		{
			this.creator = this.getParameter("creator");
		}
		if (this.getParameter("template") != null)
		{
			this.template = this.getParameter("template");
		}
		if (this.getParameter("downloadJRElnk") != null)
		{
			this.downloadJRElnk = this.getParameter("dowloadJRElnk");
		}
		if (this.getParameter("libname") != null)
		{
			this.libname = this.getParameter("libname");
		}
		
		/* NLS params (error messages + labels) */
		if(this.getParameter("button1") != null)
		{
			this.button1 = this.getParameter("button1");
		}
		if(this.getParameter("button2") != null)
		{
			this.button2 = this.getParameter("button2");
		}
		if(this.getParameter("button3") != null)
		{
			this.button3 = this.getParameter("button3");
		}	
		if(this.getParameter("choseFile") != null)
		{
			this.choseFile = this.getParameter("choseFile");
		}
		if(this.getParameter("nothingChosen") != null)
		{
			this.nothingChosen = this.getParameter("nothingChosen");
		}
		if(this.getParameter("pasteImage") != null)
		{
			this.pasteImage = this.getParameter("pasteImage");
		}
		if(this.getParameter("sendImage") != null)
		{
			this.sendImage = this.getParameter("sendImage");
		}
		if(this.getParameter("encodingMessage") != null)
		{
			this.encodingMessage = this.getParameter("encodingMessage");
		}
		if(this.getParameter("tooLarge") != null)
		{
			this.tooLarge = this.getParameter("tooLarge");
		}
		if(this.getParameter("sending") != null)
		{
			this.sending = this.getParameter("sending");
		}		
		if(this.getParameter("cantConnect") != null)
		{
			this.cantConnect = this.getParameter("cantConnect");
		}
		if(this.getParameter("imageSent") != null)
		{
			this.imageSent = this.getParameter("imageSent");
		}
		if(this.getParameter("wrongVersion") != null)
		{
			this.wrongVersion = this.getParameter("wrongVersion");
		}
		if(this.getParameter("dlLink") != null)
		{
			this.dlLink = this.getParameter("dlLink");
		}
	}
	//Read servlet response
	public void readServletResponse(HttpURLConnection servletConnection)
	{
		BufferedReader inFromServlet = null;
        try
		{
	    	inFromServlet = new BufferedReader(new InputStreamReader(servletConnection.getInputStream()));
			responseString = inFromServlet.readLine();
			setStatusText("response: " + responseString);
			inFromServlet.close();
        }
        catch (Exception e)
        {
          System.out.println("Exception: " + e.toString());
        }
    }

	//scale down image to certain templates
	public void resizeCurrentImage(Image resizeImage, String template)
	{
		//get type of template using imageTemplate.getSelectedItem();

		int w = resizeImage.getWidth(null);			//image width
	    int h = resizeImage.getHeight(null);		//image height

	    int respi = Toolkit.getDefaultToolkit().getScreenResolution();
	    //resolution of 72 or 96 pixels per inch (standard dpis)

		Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth = screenDim.width;
		int screenHeight = screenDim.height;


		if(template.equals("-- SELECT THE TEMPLATE --"))
		{
			setStatusText("Please select a template.");
		}
		else if(template.equals("Logo"))
		{
			double docWidthInch = 1.75;
			double docHeightInch = 1.75;

			double maxDim = docWidthInch * respi; //Largest length in pixels, dependant on dpi and screen size

			double scale = (double) maxDim / (double) h;
			if (w > h)
			{
				scale = (double) maxDim / (double) w;
			}	

			int scaledW = (int) (scale * w);
			int scaledH = (int) (scale * h);

			Image smallImage = createImage(scaledW, scaledH);
			Graphics gr = smallImage.getGraphics();
			gr.drawImage(resizeImage, 0, 0, scaledW, scaledH, imgViewer);

			actionPasteImage(smallImage);
		}
		else if(template.equals("PDF"))
				{
					double docWidthInch = 1.75;
					double docHeightInch = 1.75;

					double maxDim = docWidthInch * respi; //Largest length in pixels, dependant on dpi and screen size

					double scale = (double) maxDim / (double) h;
					if (w > h)
					{
						scale = (double) maxDim / (double) w;
					}

					int scaledW = (int) (scale * w);
					int scaledH = (int) (scale * h);

					Image smallImage = createImage(scaledW, scaledH);
					Graphics gr = smallImage.getGraphics();
					gr.drawImage(resizeImage, 0, 0, scaledW, scaledH, imgViewer);

					actionPasteImage(smallImage);
		}
		else if(template.equals("Check"))
				{
					double docWidthInch = 1.75;
					double docHeightInch = 1.75;

					double maxDim = docWidthInch * respi; //Largest length in pixels, dependant on dpi and screen size

					double scale = (double) maxDim / (double) h;
					if (w > h)
					{
						scale = (double) maxDim / (double) w;
					}

					int scaledW = (int) (scale * w);
					int scaledH = (int) (scale * h);

					Image smallImage = createImage(scaledW, scaledH);
					Graphics gr = smallImage.getGraphics();
					gr.drawImage(resizeImage, 0, 0, scaledW, scaledH, imgViewer);

					actionPasteImage(smallImage);
		}
		else if(template.equals("Banner"))
		{
			double docWidthInch = 6.5;
			double docHeightInch = .5;

			double maxDim = docWidthInch * respi; //Largest length in pixels, dependant on dpi and screen size

			double scale = (double) maxDim / (double) h;
			if (w > h)
			{
				scale = (double) maxDim / (double) w;
			}

			int scaledW = (int) (scale * w);
			int scaledH = (int) (scale * h);

			Image smallImage = createImage(scaledW, scaledH);
			Graphics gr = smallImage.getGraphics();
			gr.drawImage(resizeImage, 0, 0, scaledW, scaledH, imgViewer);

			actionPasteImage(smallImage);
		}

	    else if(template.equals("passport"))
	    {
			int docWidthInch = 2;
			int docHeightInch = 2;

			int maxDim = docWidthInch * respi; //Largest length in pixels, dependant on dpi and screen size

			double scale = (double) maxDim / (double) h;
			if (w > h)
			{
				scale = (double) maxDim / (double) w;
			}

			int scaledW = (int) (scale * w);
			int scaledH = (int) (scale * h);

			Image smallImage = createImage(scaledW, scaledH);
			Graphics gr = smallImage.getGraphics();
			gr.drawImage(resizeImage, 0, 0, scaledW, scaledH, imgViewer);

			actionPasteImage(smallImage);
		}
		else if(template.equals("signature"))
		{
			int docWidthInch = 3;
			int docHeightInch = 1;

			int maxDim = docWidthInch * respi; //Largest length in pixels, dependant on dpi and screen size

			double scale = (double) maxDim / (double) h;
			if (w > h)
			{
				scale = (double) maxDim / (double) w;
			}

			int scaledW = (int) (scale * w);
			int scaledH = (int) (scale * h);

			Image smallImage = createImage(scaledW, scaledH);
			Graphics gr = smallImage.getGraphics();
			gr.drawImage(resizeImage, 0, 0, scaledW, scaledH, imgViewer);

			actionPasteImage(smallImage);
		}
		else if(template.equals("card"))
		{
			double docWidthInch = 3.5;
			double docHeightInch = 2.25;

			int maxDim = (int)(docWidthInch * respi); //Largest length in pixels, dependant on dpi and screen size

			double scale = (double) maxDim / (double) h;
			if (w > h)
			{
				scale = (double) maxDim / (double) w;
			}

			int scaledW = (int) (scale * w);
			int scaledH = (int) (scale * h);

			Image smallImage = createImage(scaledW, scaledH);
			Graphics gr = smallImage.getGraphics();
			gr.drawImage(resizeImage, 0, 0, scaledW, scaledH, imgViewer);

			actionPasteImage(smallImage);
		}
		else if(template.equals("fullPade_L"))
		{
			double docWidthInch = 11.0;
			double docHeightInch = 8.5;

			int maxDim = (int)(docWidthInch * respi); //Largest length in pixels, dependant on dpi and screen size

			double scale = (double) maxDim / (double) h;
			if (w > h)
			{
				scale = (double) maxDim / (double) w;
			}

			int scaledW = (int) (scale * w);
			int scaledH = (int) (scale * h);

			Image smallImage = createImage(scaledW, scaledH);
			Graphics gr = smallImage.getGraphics();
			gr.drawImage(resizeImage, 0, 0, scaledW, scaledH, imgViewer);

			actionPasteImage(smallImage);
		}
		else if(template.equals("fullPage_P"))
		{
			double docWidthInch = 8.5;
			double docHeightInch = 11.0;

			int maxDim = (int)(docWidthInch * respi); //Largest length in pixels, dependant on dpi and screen size

			double scale = (double) maxDim / (double) h;
			if (w > h)
			{
				scale = (double) maxDim / (double) w;
			}

			int scaledW = (int) (scale * w);
			int scaledH = (int) (scale * h);

			Image smallImage = createImage(scaledW, scaledH);
			Graphics gr = smallImage.getGraphics();
			gr.drawImage(resizeImage, 0, 0, scaledW, scaledH, imgViewer);

			actionPasteImage(smallImage);
		}
		else if(template.equals("halfPage_L"))
		{
			double docWidthInch = 8.5;
			double docHeightInch = 11.0;

			int maxDim = (int)(docWidthInch * respi); //Largest length in pixels, dependant on dpi and screen size

			double scale = (double) maxDim / (double) h;
			if (w > h)
			{
				scale = (double) maxDim / (double) w;
			}

			int scaledW = (int) (scale * w);
			int scaledH = (int) (scale * h);

			Image smallImage = createImage(scaledW, scaledH);
			Graphics gr = smallImage.getGraphics();
			gr.drawImage(resizeImage, 0, 0, scaledW, scaledH, imgViewer);

			actionPasteImage(smallImage);
		}
		else
		{
			setStatusText("No template specified.");
		}

	}
	//change text in status bar
	public void setStatusText(String msg)
	{
		this.lblStatus.setText(msg);
		this.lblStatus.setForeground(new Color(0xff0000));
		this.lblStatus.invalidate();
		this.lblStatus.revalidate();
		repaint();
	}
	//write out info to servlet as byte[]
	public void writePaddedString(OutputStream out, String str, int len) throws IOException
	{

		if (str.length() < len)
		{
			StringBuffer sb = new StringBuffer();
			for (int i=1; i<=(len - str.length()); i++)
				sb.append(' ');
			out.write((str + sb.toString()).getBytes());
			System.out.println("'" + (str + sb.toString()) + "' content wrote");
		}
		else
		{
			out.write(str.getBytes());
		}
	}

	   public byte [] toBytes(String fileName)
	   {
	   	     StringBuffer sb = new StringBuffer();
	   	try
	        {
	   	     FileInputStream fis= new FileInputStream(fileName);
	         BufferedReader br = new BufferedReader(new InputStreamReader(fis));



	         String s = br.readLine();
	         while (s != null)
	         {
	   			 sb.append(s);
	             s = br.readLine();
	         }
	         }
	         catch(IOException e)
	         {
	              System.out.println(e.getMessage());
	         }
	         return sb.toString().getBytes();
		}


}// end of clipapplet class
