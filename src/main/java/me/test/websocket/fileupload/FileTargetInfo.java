package me.test.websocket.fileupload;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 内部保存文件状态
 */
public class FileTargetInfo implements Serializable {

    private static final long serialVersionUID = -892304047563801858L;

    /**上传文件基本目录*///todo
    private String savePath = BreakpointUploadConfig.savePath;
    public int blobSize = BreakpointUploadConfig.blobSize;
    /**上传文件信息*/
    private FileInfo fileInfo;
    /**文件上传进度*/
    private float completePercent=0;
    /**服务器端文件名*/
    private String fileName;
    /**
     * 文件大小
     */
    private long fileSize=0;


    private long indexStart=0;
    private long indexEnd=0;

    private File file;
    private File tempFile;
    private RandomAccessFile raFile;
    private FileChannel fileChannel;

    public FileTargetInfo(FileInfo fileInfo,String savePath){
        this.savePath = savePath;
        this.initInfo(fileInfo);
    }



    public FileTargetInfo(FileInfo fileInfo){
        this.initInfo(fileInfo);
    }

    private void initInfo(FileInfo fileInfo){
        this.fileSize = fileInfo.getFileSize();
        this.fileInfo = fileInfo;
        this.fileName=fileInfo.getFileId()+"."+fileInfo.getFileName().substring(fileInfo.getFileName().lastIndexOf(".")+1);

        if(this.blobSize>this.fileSize){
            indexEnd = this.fileSize;
        }else{
            indexEnd = this.blobSize;
        }

        File filemkdirs =new File(this.savePath);
        //如果文件夹不存在则创建
        if(!filemkdirs .exists()  && !filemkdirs .isDirectory())
        {
            filemkdirs .mkdirs();
        }

        //开始创建文件模板
        this.file=new File(savePath+this.fileName);
        this.tempFile=new File(savePath+this.fileInfo.getFileId()+".temp");
        try {
            this.raFile=new RandomAccessFile(this.tempFile, "rw");
            this.raFile.setLength(this.fileInfo.getFileSize());
            this.fileChannel=this.raFile.getChannel();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 保存数据
     * @return
     */
    public synchronized FileTargetInfo saveByteBuffer(ByteBuffer bb){
        try {
            this.fileChannel.write(bb, this.indexStart);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        if(this.indexEnd>=this.fileSize){//上传完毕
            this.completePercent = 1;
            this.indexEnd = -1;
            this.indexStart = -1;
            this.fileUploadComplete();
            return this;
        }

        this.completePercent = (float)this.indexEnd/(float)this.fileSize;

        //头变尾
        this.indexStart = this.indexEnd;

        if(this.fileSize-this.indexEnd>this.blobSize){
            this.indexEnd+=this.blobSize;
        }else{
            this.indexEnd = this.fileSize;
        }
        return this;
    }


    public void fileUploadComplete(){
        this.closeFileWriteAccessChannel();
        if(this.completePercent>=1){
            this.tempFile.renameTo(this.file);
        }
        this.tempFile.delete();
    }

    public void closeFileWriteAccessChannel(){
        try {
            this.fileChannel.close();
            this.raFile.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    public int getBlobSize() {
        return blobSize;
    }

    public void setBlobSize(int blobSize) {
        this.blobSize = blobSize;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public File getTempFile() {
        return tempFile;
    }

    public void setTempFile(File tempFile) {
        this.tempFile = tempFile;
    }

    public RandomAccessFile getRaFile() {
        return raFile;
    }

    public void setRaFile(RandomAccessFile raFile) {
        this.raFile = raFile;
    }

    public FileChannel getFileChannel() {
        return fileChannel;
    }

    public void setFileChannel(FileChannel fileChannel) {
        this.fileChannel = fileChannel;
    }

    public FileInfo getFileInfo() {
        return fileInfo;
    }

    public void setFileInfo(FileInfo fileInfo) {
        this.fileInfo = fileInfo;
    }

    public float getCompletePercent() {
        return completePercent;
    }

    public void setCompletePercent(float completePercent) {
        this.completePercent = completePercent;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getIndexStart() {
        return indexStart;
    }

    public void setIndexStart(long indexStart) {
        this.indexStart = indexStart;
    }

    public long getIndexEnd() {
        return indexEnd;
    }

    public void setIndexEnd(long indexEnd) {
        this.indexEnd = indexEnd;
    }


}
