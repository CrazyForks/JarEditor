package com.liubs.jareditor.action;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.liubs.jareditor.jarbuild.JarBuildResult;
import com.liubs.jareditor.jarbuild.JarBuilder;
import com.liubs.jareditor.sdk.NoticeInfo;
import com.liubs.jareditor.template.TemplateManager;
import com.liubs.jareditor.util.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;

/**
 * jar内新增jar
 * @author Liubsyy
 * @date 2024/11/26
 */
public class JarEditorAddJarInJar extends JavaEditorAddFile {

    @Override
    protected String preInput(Project project, String entryPathFromJar) {
        String userInput = Messages.showInputDialog(
                project,
                "Enter JAR name:",
                "Create New JAR",
                Messages.getQuestionIcon(),
                ".jar",
                null
        );
        if(StringUtils.isEmpty(userInput)) {
            return null;
        }

        if(null == entryPathFromJar){
            return userInput.endsWith(".jar") ? userInput : userInput+".jar";
        }
        return userInput.endsWith(".jar") ?  entryPathFromJar+"/"+userInput : entryPathFromJar+"/"+userInput+".jar";
    }

    @Override
    protected boolean addFileInJar(String jarPath, String entryPath) {
        JarBuilder jarBuilder = new JarBuilder(jarPath);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (JarOutputStream jarOutputStream = new JarOutputStream(byteArrayOutputStream)) {
            JarEntry META_INF = JarBuilder.createStoredEntry("META-INF/", "".getBytes());
            jarOutputStream.putNextEntry(META_INF);
            jarOutputStream.closeEntry();

            byte[] bytes = TemplateManager.getText("MF", entryPath).getBytes(StandardCharsets.UTF_8);
            JarEntry MANIFEST_MF = JarBuilder.createStoredEntry("META-INF/MANIFEST.MF",bytes);
            jarOutputStream.putNextEntry(MANIFEST_MF);
            jarOutputStream.write(bytes);
            jarOutputStream.closeEntry();
        } catch (IOException e) {
            e.printStackTrace();
        }

        byte[] jarBytes = byteArrayOutputStream.toByteArray();

        JarBuildResult jarBuildResult = jarBuilder.addFile(entryPath,jarBytes);
        if(!jarBuildResult.isSuccess()) {
            NoticeInfo.error("Add file err: \n%s",jarBuildResult.getErr());
            return false;
        }
        return true;
    }
}