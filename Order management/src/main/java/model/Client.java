package model;

/**
 * Clasa care reprezinta obiectul de tip client, care are ca atribute un id, nume si adresa.
 * Contine constructori, metode de get, de set si toString.
 */
public class Client {

    private int client_id;
    private String client_name;
    private String address;

    public Client(int client_id, String client_name, String address) {
        this.client_id = client_id;
        this.client_name = client_name;
        this.address = address;
    }

    public Client() {

    }

    public int getClient_id() {
        return this.client_id;
    }


    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Metoda care ajuta la afisarea obiectului
     *
     * @return String-ul care va fi afisat, cu detaliile despre obiect
     */
    @Override
    public String toString() {
        return "Client: " + client_id + ", name: " + client_name + ", address: " + address + "\n";
    }
}
