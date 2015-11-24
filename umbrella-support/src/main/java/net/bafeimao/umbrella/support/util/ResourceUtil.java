package net.bafeimao.umbrella.support.util;

/**
 * Created by ktgu on 15/11/22.
 */
public class ResourceUtil {

    public static void getTypes() {
        Package.getPackages();
    }

    public static void main(String[] args) {
        Package pkg = Package.getPackage("net.bafeimao.*");
        Package[] packages = pkg.getPackages();
        System.out.println(packages);
    }
}
