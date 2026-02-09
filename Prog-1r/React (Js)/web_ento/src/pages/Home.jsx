import premisImg from '../assets/premis.webp'; 
import mespremisImg from '../assets/mespremis.webp';
const Home = () => (
  <div>
    <section className="parallax">
      <div style={{ padding: '2rem', backgroundColor: 'rgba(0,0,0,0.5)' }}>
        <h2>Benvingut a Elden Ring</h2>
        <p>Explora el món de les Terres Intermèdies.</p>
      </div>
    </section>
    <section className="main-content">
      <h2>Sobre el Joc</h2>
      <p>Aventura, misteri i desafiaments t'esperen...</p>
    </section>
    <section>
      <h2 class="goldenorder">L'ordre daurada s'ha trencat.</h2>
      <p>Aixeca't sensellum, i que la gràcia et guii a recuperar el poder del cercle d'Elden, aixeca't com a lord del cercle.
      <br/><br/>
      Les terres Intermèdies van ser regnades per la Reina Márika, l'Eterna, el cercle d'elden s'ha trencat.
      <br/><br/>
      Tots els fills semidéus de Márika, es van quedar els fragments trencats del cercle d'Elden, coneguts com grans Runes.
      <br/><br/>
      Una guerra que va resultar en l'abandonament total de la Gran Voluntat. I ara la gràcia ens guia a nosaltres els sensellum a reparar el cercle 
      </p>
    </section>
    <img class="premis" src={premisImg}/> <br/>
    <img class="premis2" src={mespremisImg}/> <br/>
  </div>
);

export default Home;
