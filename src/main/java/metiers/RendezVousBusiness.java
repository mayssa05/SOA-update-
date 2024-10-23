package metiers;

import entities.Logement;
import entities.RendezVous;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class RendezVousBusiness {
    public static List<RendezVous> listeRendezVous = new ArrayList<>();

    LogementBusiness logementMetier = new LogementBusiness();

    public RendezVousBusiness() {
    }

    public boolean addRendezVous(RendezVous rendezVous) {
        int refLogement = rendezVous.getLogement().getReference();
        Logement logement = logementMetier.getLogementsByReference(refLogement);

        if (logement != null) {
            // Vérification : éviter les doublons de rendez-vous pour le même logement, date, heure
            for (RendezVous r : listeRendezVous) {
                if (r.getDate().equals(rendezVous.getDate()) &&
                        r.getHeure().equals(rendezVous.getHeure()) &&
                        r.getLogement().getReference() == refLogement) {
                    return false;  // Doublon de rendez-vous
                }
            }
            rendezVous.setLogement(logement);
            return listeRendezVous.add(rendezVous);
        }
        return false;
    }

    public List<RendezVous> getListeRendezVous() {
        return listeRendezVous;
    }

    public List<RendezVous> getListeRendezVousByLogementReference(int reference) {
        List<RendezVous> liste = new ArrayList<>();
        for (RendezVous r : listeRendezVous) {
            if (r.getLogement().getReference() == reference)
                liste.add(r);
        }
        return liste;
    }

    public RendezVous getRendezVousById(int id) {
        return listeRendezVous.stream()
                .filter(r -> r.getId() == id)
                .findFirst()
                .orElse(null); // Renvoie null si non trouvé
    }


    public boolean deleteRendezVous(int id) {
        Iterator<RendezVous> iterator = listeRendezVous.iterator();
        while (iterator.hasNext()) {
            RendezVous r = iterator.next();
            if (r.getId() == id) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    public boolean updateRendezVous(int idRendezVous, RendezVous updatedRendezVous) {
        for (int i = 0; i < listeRendezVous.size(); i++) {
            RendezVous r = listeRendezVous.get(i);
            if (r.getId() == idRendezVous) {
                Logement logement = logementMetier.getLogementsByReference(updatedRendezVous.getLogement().getReference());
                if (logement != null) {
                    updatedRendezVous.setLogement(logement);  // Mettre à jour le logement
                    listeRendezVous.set(i, updatedRendezVous); // Mettre à jour le rendez-vous
                    return true;
                }
            }
        }
        return false;
    }


}
