package org.conan.tools.core.build;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.conan.tools.core.file.ClazzTree;
import org.conan.tools.core.clazz.ModelClazzBean;
import org.conan.tools.core.file.PackageTree;
import org.conan.tools.core.factory.VelocityFactory;
import org.conan.tools.util.match.DateMatch;
import org.conan.tools.core.io.OutputFile;
import org.conan.tools.core.model.CopyRightObject;
import org.conan.tools.core.model.ModelPO;

/**
 * 
 * @author Conan
 */
public class BuildModelFile {

    public BuildModelFile(ModelPO po) {
        PackageTree pack = new PackageTree(po);
        ClazzTree clazz = new ClazzTree(po.getModel(), pack);
        ModelClazzBean mcb = new ModelClazzBean(po);

        Map map = new HashMap();
        map.put("date", DateMatch.getNowDate());
        map.put("author", CopyRightObject.AUTHOR);
        map.put("copyright", CopyRightObject.COPYRIGHT);
        map.put("timestamp", System.currentTimeMillis() + "" + new Random().nextInt(3) + "L");

        map.put("model", po.getModel());
        map.put("model_package", pack.getModelPackage());
        map.put("model_imports", po.getImports());
        map.put("model_constructorMethods", mcb.getConstructorMethod());
        map.put("model_properties", mcb.getProperties());
        map.put("model_setMethods", mcb.getSetMethod());
        map.put("model_getMethods", mcb.getGetMethod());

        VelocityFactory vf = new VelocityFactory(VelocityFactory.MODEL_VM, map);
        new OutputFile(clazz.getModelFile(), vf.getWriter());
    }
}
