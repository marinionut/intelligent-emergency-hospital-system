package ro.ionutmarin.iehs.request;

public class ComputeInstanceRequest {
    String zone;
    String image;
    String vmName;
    int osImageIndex;


    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getOsImageIndex() {
        return osImageIndex;
    }

    public void setOsImageIndex(int osImageIndex) {
        this.osImageIndex = osImageIndex;
    }

    public String getVmName() {
        return vmName;
    }

    public void setVmName(String vmName) {
        this.vmName = vmName;
    }
}
