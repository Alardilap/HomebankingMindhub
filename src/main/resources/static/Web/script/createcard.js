let { createApp } = Vue;

createApp({
    data() {
        return {
            selectedtype: null,
            selectedcolor: null
        };
    },
    created() {

    },
    methods: {
        createcard() {
            axios.post("/api/clients/current/cards", `color=${this.selectedcolor}&type=${this.selectedtype}`)

                .then((response) => {
                    console.log(response)
                    console.log(this.selectedtype)
                    Swal.fire({
                        position: 'center',
                        icon: 'success',
                        iconColor: 'grey',
                        title: 'Account Created successfully',
                        showConfirmButton: false,
                        timer: 900
                    })
                    setTimeout(() => {
                        location.href = "./cards.html"
                    }, 1700)

                }).catch((err) => {
                    let errorMessage = err.response.data;
                    console.log(err)
                    Swal.fire({
                        position: 'center',
                        icon: 'error',
                        iconColor: 'red',
                        text: errorMessage,
                        showConfirmButton: false,
                        timer: 1500,
                        customClass: {
                            text: 'custom-swal-text' // Definir una clase personalizada
                        }
                    })
                })

        }


    },
}).mount('#app');