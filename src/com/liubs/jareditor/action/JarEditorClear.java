package com.liubs.jareditor.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.liubs.jareditor.sdk.NoticeInfo;
import com.liubs.jareditor.util.MyFileUtil;
import com.liubs.jareditor.util.MyPathUtil;
import org.jetbrains.annotations.NotNull;

/**
 * 删除Save的临时文件夹
 * @author Liubsyy
 * @date 2024/6/26
 */
public class JarEditorClear extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Project project = e.getProject();
        if(project == null) {
            NoticeInfo.warning("Please open a project");
            return;
        }
        VirtualFile selectedFile = null;
        FileEditorManager fileEditorManager = FileEditorManager.getInstance(project);
        if(null != fileEditorManager) {
            VirtualFile[] editorSelectFiles = fileEditorManager.getSelectedFiles();
            if(editorSelectFiles.length > 0) {
                selectedFile = editorSelectFiles[0];
            }
        }


        //VirtualFile selectedFile = e.getData(CommonDataKeys.VIRTUAL_FILE);
        if(null == selectedFile) {
            NoticeInfo.warning("No file selected");
            return;
        }

        final String jarPath = "jar".equals(selectedFile.getExtension()) ?
                selectedFile.getPath().replace(".jar!/",".jar") : MyPathUtil.getJarPathFromJar(selectedFile.getPath());
        if(null == jarPath) {
            NoticeInfo.warning("This operation only in JAR !!!");
            return;
        }

        VirtualFile finalSelectedFile = selectedFile;
        ProgressManager.getInstance().run(new Task.Backgroundable(null, "Clear temp directory ...", false) {
            @Override
            public void run(@NotNull ProgressIndicator progressIndicator) {
                try {

                    //删除临时保存的目录
                    MyFileUtil.deleteDir(MyPathUtil.getJarEditTemp(finalSelectedFile.getPath()));

                    NoticeInfo.info("Clear success !");
                }catch (Throwable e) {
                    NoticeInfo.error("Clear files err",e);
                }
            }
        });

    }
}
