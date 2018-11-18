package cn.hgf.EMail;

import java.io.*;
import java.net.Socket;
import java.util.*;

import org.apache.commons.lang3.StringUtils;


public class MailValid {

    public static void main(String[] args) {
        System.out.println(MailValid.valid("1641197217@111.com", "1694289437@qq.com","xuhexzqtcpqjfcji"));
    }

    /**
     * 验证邮箱是否存在
     * <br>
     * 由于要读取IO，会造成线程阻塞
     *
     * @param toMail
     *         要验证的邮箱
     * @param fromMail
     *         发出验证请求邮箱
     * @return
     *         邮箱是否可达
     */
    public static boolean valid(String toMail, String fromMail,String password) {
        if (StringUtils.isBlank(toMail)
                || StringUtils.isBlank(fromMail)
                || StringUtils.isBlank(password)
                || !SimpleMailSender.verifyMail(toMail)
                || !SimpleMailSender.verifyMail(fromMail)){

            return false;
        }

        String fromHost = fromMail.substring(fromMail.lastIndexOf("@") + 1);

        Socket socket = null;
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        try {
            socket = new Socket("smtp." + fromHost, 25);

            bufferedReader = new BufferedReader(new InputStreamReader(new BufferedInputStream(socket.getInputStream())));
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            // 超时时间(毫秒)
            long timeout = 6000;
            // 睡眠时间片段(50毫秒)
            int sleepSect = 50;

            // 连接(服务器是否就绪)
            if(getResponseCode(timeout, sleepSect, bufferedReader) != 220) {
                return false;
            }

            // 握手
            bufferedWriter.write("HELO " + fromHost + "\r\n");
            bufferedWriter.flush();
            if(getResponseCode(timeout, sleepSect, bufferedReader) != 250) {
                return false;
            }

            // 身份 验证
            bufferedWriter.write("AUTH LOGIN" + "\r\n");
            bufferedWriter.flush();
            if(getResponseCode(timeout, sleepSect, bufferedReader) != 334) {
                return false;
            }

            bufferedWriter.write( enderBase64(fromMail) + "\r\n");
            bufferedWriter.flush();
            if(getResponseCode(timeout, sleepSect, bufferedReader) != 334) {
                return false;
            }

            bufferedWriter.write(enderBase64(password) + "\r\n");
            bufferedWriter.flush();
            if(getResponseCode(timeout, sleepSect, bufferedReader) != 235) {
                return false;
            }

            //发件人
            bufferedWriter.write("MAIL FROM: <" + fromMail + ">\r\n");
            bufferedWriter.flush();
            if(getResponseCode(timeout, sleepSect, bufferedReader) != 250) {
                return false;
            }
            // 验证收件人
            bufferedWriter.write("RCPT TO: <" + toMail + ">\r\n");
            bufferedWriter.flush();
            if(getResponseCode(timeout, sleepSect, bufferedReader) != 250) {
                return false;
            }
            // 断开
            bufferedWriter.write("QUIT\r\n");
            bufferedWriter.flush();
            return true;
        } catch (NumberFormatException | IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
            if (bufferedWriter != null){
                bufferedWriter.close();
            }
            if (bufferedReader != null){
                bufferedReader.close();
            }
            if (socket != null){

                socket.close();
            }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private static int getResponseCode(long timeout, int sleepSect, BufferedReader bufferedReader) throws InterruptedException, NumberFormatException, IOException {
        int code = 0;
        for(long i = sleepSect; i < timeout; i += sleepSect) {
            Thread.sleep(sleepSect);
            if(bufferedReader.ready()) {
                String outline = bufferedReader.readLine();
                // FIXME 读完……
                while(bufferedReader.ready())
                    /*System.out.println(*/bufferedReader.readLine()/*)*/;
                /*System.out.println(outline);*/
                code = Integer.parseInt(outline.substring(0, 3));
                break;
            }
        }
        return code;
    }

    public static String enderBase64(String string){

        byte[] bytes = null;
        try {

            bytes = string.getBytes("UTF-8");
            Base64.Encoder encoder = Base64.getEncoder();
            return encoder.encodeToString(bytes);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }

}