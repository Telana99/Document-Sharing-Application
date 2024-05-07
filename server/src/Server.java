import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    static ArrayList<MyFile> myFiles = new ArrayList<>();

    public static void main(String[] args){
        //represent each file
        int fileId = 0;

        //design the UI

        JFrame jFrame = new JFrame("THT's Server");
        jFrame.setSize(400,400);
        jFrame.setLayout(new BoxLayout(jFrame.getContentPane(), BoxLayout.Y_AXIS));
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));

        JScrollPane jScrollPane = new JScrollPane(jPanel);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        JLabel jlTitle = new JLabel("THT's File Receiver");
        jlTitle.setFont(new Font("Arial", Font.BOLD,25));
        jlTitle.setBorder(new EmptyBorder(20,0,10,0));
        jlTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        jFrame.add(jlTitle);
        jFrame.add(jScrollPane);
        jFrame.setVisible(true);

        /*
        server socket waits for the request and when it receive it
        can perform the operations and give result according to the request.
        */

        ServerSocket serverSocket = new ServerSocket(1234);

        while(true){

            try{

                Socket socket = serverSocket.accept();

                DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());

                int fileNameLength = dataInputStream.readInt();

                if(fileNameLength >0){
                    byte[] fileNameBytes = new byte[fileNameLength];
                    dataInputStream.readFully(fileNameBytes, 0,fileNameBytes.length);
                    String fileName = new String(fileNameBytes);

                    int fileContentLength = dataInputStream.readInt();

                    if(fileContentLength > 0) {
                        byte[] fileContentBytes = new byte[fileContentLength];
                        dataInputStream.readFully(fileContentBytes,0 , fileContentLength);

                        JPanel jpFileRow = new JPanel();
                        jpFileRow.setLayout(new BoxLayout(jpFileRow, BoxLayout.Y_AXIS));

                        JLabel jlFileName = new JLabel(fileName);
                        jlFileName.setFont(new Font("Arial", Font.BOLD,20));
                        jlFileName.setBorder(new EmptyBorder(10,0,10,0));

                        if(getFilExtension(fileName).equalsIgnoreCase("txt")){
                            jpFileRow.setName(String.valueOf(fileId));
                            jpFileRow.addMouseListener(getMyMouseListner());

                            jpFileRow.add(jlFileName);
                            jPanel.add(jpFileRow);
                            jFrame.validate();

                        }

                    }

                }
            }
        }

    }
    public  static MouseListener getMyMouseListner(){
        return new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        }
    }

    public static String getFilExtension(String fileName){
        int i = fileName.lastIndexOf('.');

        if(i>0){
            return fileName.substring(i+1);
        }else {
            return "No extension found.";
        }
    }
}
