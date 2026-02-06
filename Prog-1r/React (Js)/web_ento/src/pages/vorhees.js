import swordImage from '../assets/espasa.webp';
import cherishingImage from '../assets/dits.png';
import albinauricImage from '../assets/mask.png';
import seppukuImage from '../assets/seppuku.png';
import eldenRingImage from '../assets/logo.png';


const vorheesData = [
    {
      id: "1",
      titol: "Elden Ring",
      imatge: eldenRingImage,
      productes: [
        {
            nom: "Espasa de la Llum (sword of light)",
            imatge: swordImage,
            categoria: "Armes" 
          },
          {
            nom: "Dits estimats (cherishing fingers)",
            imatge: cherishingImage,
            categoria: "Ecanteris"
          },
          {
            nom: "Màscara d'Albinàuric (albinauric mask)",
            imatge: albinauricImage,
            categoria: "Armadures"
          },
          {
            nom: "Seppuku (Seppuku)",
            imatge: seppukuImage,
            categoria: "Cendres de guerra"
          }
      ]
    }
  ];
  
  export default vorheesData;