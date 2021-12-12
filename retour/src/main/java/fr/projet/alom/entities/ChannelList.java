package fr.projet.alom.entities;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class ChannelList {

    private List<String> channels;

    public ChannelList(List<String> channels) {
        this.channels = channels;
    }

    public ChannelList() {
    }

    public List<String> getChannels() {
        return channels;
    }

    public void setChannels(List<String> channels) {
        this.channels = channels;
    }
}
