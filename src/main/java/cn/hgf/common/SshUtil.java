package cn.hgf.common;

import cn.hgf.springdemo.common.CommonParam;
import com.jcraft.jsch.*;

import java.io.File;
import java.util.Vector;

/**
 * @Author: FanYing
 * @Date: 2018-08-17 16:30
 * @Desciption:
 */
public class SshUtil {

    public String host;
    public String password;
    public String user;
    public int port;

    private Session session;

    public SshUtil(String host,int port,String password,String user) {
        this.host = host;
        this.password = password;
        this.user = user;
        this.port = port;
        connection();
    }

    public void uploadFile(String localFilePath,String remoteFilePath){

        connection();
        Channel sftp = null;
        ChannelSftp channelSftp = null;
        try {
            sftp = session.openChannel("sftp");
            channelSftp = (ChannelSftp) sftp;

            channelSftp.connect();
            Vector ls = channelSftp.ls(remoteFilePath);
            if (ls == null){
                channelSftp.mkdir(remoteFilePath);
            }
            channelSftp.put(localFilePath,remoteFilePath);

            if(channelSftp!=null && channelSftp.isConnected()){
                channelSftp.disconnect();
            }

        } catch (JSchException e) {
            e.printStackTrace();
        } catch (SftpException e) {
            e.printStackTrace();
        }finally {
            close();
        }

    }


    /**
     *  远程连接
     */
    public void connection(){

        if (session == null){
            JSch jSch = new JSch();
            try {
                session = jSch.getSession(user, host, port);

                session.setPassword(password);
                session.setConfig("StrictHostKeyChecking", "no");
                session.connect(30000);
            } catch (JSchException e) {
                e.printStackTrace();
            }
        }else {
            System.out.println("session 已经存在！！！");
        }
    }

    /**
     *  关闭连接
     *
     */
    public void close(){
        if (session != null && session.isConnected()){
            session.disconnect();
            session = null;
        }
    }

    public static void main(String[] args){
        SshUtil sshUtil = new SshUtil(CommonParam.SSH_HOST, 22, CommonParam.SSH_PASS, CommonParam.SSH_USER);
        sshUtil.uploadFile(CommonParam.FILE_PATH + "expressNo.txt","/opt/rh");
    }
}
