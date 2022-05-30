import firebase from 'firebase/app'
import 'firebase/storage'

const firebaseConfig = {
    apiKey: "AIzaSyC1DmaygVH8WKCK8fDe5UjU3VGnBQzuuto",
    authDomain: "ecommerce-elite-nashtech.firebaseapp.com",
    projectId: "ecommerce-elite-nashtech",
    storageBucket: "ecommerce-elite-nashtech.appspot.com",
    messagingSenderId: "339354666387",
    appId: "1:339354666387:web:802a3d278bc392d4f09466",
    measurementId: "G-WTDCYPZJK6"
};

firebase.initializeApp(firebaseConfig);
const storage = firebase.storage()

export default storage
