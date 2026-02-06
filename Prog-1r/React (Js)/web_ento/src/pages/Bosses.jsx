import nokstella from '../assets/dragonkin_nokstella_bosses_elden_ring_wiki_600px.jpg';
import niall from '../assets/commander-niall-elden-ring-wiki.jpg'; 
import death from '../assets/death-knight-elden-ring-wiki.jpg';
import scadutree from '../assets/scadutree_avatar_bosses_elden_ring_wiki_1200px.png';
import bloodhound from '../assets/bloodhound-knight-1-hq-elden-ring-wiki-guide.jpg'
import "./bosses.css";
const Bosses = () => (
    <div className="page">
      <h2>Bosses</h2>
      <p>Consulta la llista de bosses i com derrotar-los.</p>
      <br/>
      <h3>Dragonskin Soldier of Nokstella</h3>
      <img class="nokstella" src={nokstella}/> <br/>
      <b><p2>HP base: 4,372  DEF base: 106  POISE: 120  PARRYable : no (melee) / no (magia)</p2></b>
      <p>TRUC: El truc per derrotar a aquest jefe és estar prop seu, voltar per la seva esquena i esperar els seus moviments per contra-atacar</p>
      <br/><br/><br/>

      <h3>Commander Niall</h3>
      <img class="niall" src={niall}/> <br/>
      <b><p2>HP base: 15,541  DEF base: 117  POISE: 90  PARRYable : yes (melee) / no (magia)</p2></b>
      <p>TRUC: El truc per derrotar a aquest jefe és fer servir l'item Bewitching Branches a la primera fase per encantar una de les seves invocacions perquè lluiti temporalment al teu bàndol.
          Amb aquesta estratègia, la primera fase hauria de ser més fàcil, la segona fase es torna una mica més ràpid per tant hauries de canviar la estratègia d'atac que tens.  <br/>En el pitjor dels 
          casos hi ha un pilar sobre una muralla on pots posar-t'hi d'una forma en la qual pots disparar fletxes i aquestes aniran totes a Niall.
      </p>
      <br/><br/><br/>

      <h3>Death Knight</h3>
      <img class="death" src={death}/><br/>
      <b><p2>HP base: 20,976  DEF base: 122  POISE: 80  PARRYable : yes (melee) / no (magia)</p2></b>
      <p>TRUC: Tens tres opcions, timejar els parrys fins acabar amb ell, atacar a distància com puguis amb conjurs/consumibles/etc o amb la tàctica Cheese fent servir la Nightrider Glaive infusionada amb Braggart Roar.</p>
      <br/><br/><br/>

      <h3>Scadutree Avatar</h3>
      <img class="scadutree" src={scadutree}/><br/>
      <b><p2>HP base: 7,560 (fase 1), 13,608 (fase 2), 13,608 (fase 3)  DEF base: 123  POISE: 120  PARRYable : yes (magia) / no (melee)</p2></b>
      <p>TRUC: Fer servir armes imbuides amb foc o màgia piromàntica / dracònica li fa més mal que cap altra cosa. Si es possible apunta al seu cap que li faras més mal.</p>
      <br/><br/><br/>

      <h3>Bloodhound Knight</h3>
      <img class="bloodhound" src={bloodhound}/><br/>
      <b><p2>HP base: 1,985  DEF base: 107  POISE: 65  PARRYable : yes (melee) / no (magia)</p2></b>
      <p>TRUC: Anar ràpid i amb armes contundents, preferiblement armes pesades o colossals. Al tenir poca vida se li pot fer molt mal en poc temps però s'ha de tenir en compte <br/> 
      que és bastant ràpid també i es mou molt.</p>
      <br/><br/><br/>
      
    </div>
  );
  
  export default Bosses;
  