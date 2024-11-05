const mp = new MercadoPago('TESTUSER1956962149',{
    locale:'es-AR'
});//para crear el boton pagar con mp

const bricksBuilder = mp.bricks(); //para que la api funcione debe existir

const createCheckoutButton = (preferenceId_OK) =>{
    const generateButton = async ()=>{
        if (window.checkoutButton) window.checkoutButton.unmount() //evita pestañas multiples
        bricksBuilder.create('wallet', 'wallet_conteiner',{//se encuentra as en la documentasion
            initialization: {
                preferenceId: preferenceId_OK,
            },
        });
    }
    generateButton()
}



const MP = async () => { //llamado al servidor
    try{
        misFiguras = {};
        const response= await fetch('api/mp',{//asincronica
            method: 'POST',
            headers:{
                'Accept': 'Application/json',
                'Content-Type': 'Application/json'
            },
            body: JSON.stringify(misFiguras)
        })
        const preference = await response.text()
        createCheckoutButton(preference)
        console.log('Preference: "'+preference+'"')
    }catch (e){
        alert("error: "+e)
    }
}