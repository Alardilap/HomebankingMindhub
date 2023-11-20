let { createApp } = Vue;

createApp({
    data() {
        return {
            inputName: "",
            inputLastName: "",
            inputPassword: "",
            inputEmail: "",
            loginEmail: "",
            loginPassword: "",
        };
    },
    created() {

    },
    methods: {
        // register() {
        //     console.log(this.inputEmail, this.inputName)
        //     axios.post("/api/clients",
        //         `name=${this.inputName}&lastName=${this.inputLastName}&email=${this.inputEmail}&password=${this.inputPassword}`
        //     )
        //         .then((response) => {
        //             axios.post("/api/login", `email=${this.inputEmail}&password=${this.inputPassword}`)
        //                 .then((response) => {
        //                     this.createAccount()
        //                     Swal.fire({
        //                         position: 'center',
        //                         icon: 'success',
        //                         iconColor: 'grey',
        //                         title: 'Login Ok ',
        //                         showConfirmButton: false,
        //                         timer: 900
        //                     }), setTimeout(() => {
        //                         location.href = "Web/Pages/accounts.html"
        //                     }, 1700)
        //                 }).catch((err) => {
        //                     if (err) {
        //                         let mesageerrorregister = err.response.data
        //                         Swal.fire({
        //                             position: 'center',
        //                             icon: 'error',
        //                             iconColor: 'red',
        //                             text: mesageerrorregister,
        //                             showConfirmButton: false,
        //                             timer: 1500,
        //                             customClass: {
        //                                 text: 'custom-swal-text' // Definir una clase personalizada
        //                             }
        //                         })
        //                     }

        //                 }
        //                 )
        //         })
        // },
        register() {
            console.log(this.inputEmail, this.inputName);

            axios.post("/api/clients", `name=${this.inputName}&lastName=${this.inputLastName}&email=${this.inputEmail}&password=${this.inputPassword}`)
                .then((response) => {
                    return axios.post("/api/login", `email=${this.inputEmail}&password=${this.inputPassword}`);
                })
                .then((response) => {

                    Swal.fire({
                        position: 'center',
                        icon: 'success',
                        iconColor: 'grey',
                        title: 'Login Ok ',
                        showConfirmButton: false,
                        timer: 900
                    }), setTimeout(() => {
                        this.createAccount()
                        location.href = "Web/Pages/accounts.html"
                    }, 1700)
                })
                .catch((error) => {
                    let mesageerrorregister = error.response ? error.response.data : 'Error desconocido';
                    Swal.fire({
                        position: 'center',
                        icon: 'error',
                        iconColor: 'red',
                        text: mesageerrorregister,
                        showConfirmButton: false,
                        timer: 1500,
                        customClass: {
                            text: 'custom-swal-text'
                        }
                    });
                });
        },

        clearData() {
            this.inputName = "",
                this.inputLastName = "",
                this.inputPassword = "";
            this.inputEmail = "";
        },
        login() {
            axios.post("/api/login", `email=${this.loginEmail}&password=${this.loginPassword}`)
                .then((response) => {
                    Swal.fire({
                        position: 'center',
                        icon: 'success',
                        iconColor: 'grey',
                        title: 'Login Ok ',
                        showConfirmButton: false,
                        timer: 900
                    })
                    setTimeout(() => {
                        if (this.loginEmail === "AdminAgileBank@gmail.com") {
                            location.href = "/Web/Pages/manager.html"
                        } else {
                            location.href = "/Web/Pages/accounts.html"
                        }

                    }, 1700)

                }).catch((err) => {
                    if (err.response.status === 401) {
                        let mesageerror = "Incorrect email or password"
                        Swal.fire({
                            position: 'center',
                            icon: 'error',
                            iconColor: 'red',
                            text: mesageerror,
                            showConfirmButton: false,
                            timer: 1500,
                            customClass: {
                                text: 'custom-swal-text' // Definir una clase personalizada
                            }
                        })
                    }
                    console.log(err)
                }
                )
        },
        signOut() {
            axios.post("/api/logout")
                .then((response) => {
                    Swal.fire({
                        position: 'center',
                        icon: 'success',
                        iconColor: 'grey',
                        title: 'Logout ok',
                        showConfirmButton: false,
                        timer: 900
                    })
                    setTimeout(() => {
                        location.href = "/index.html"
                    }, 1700)
                }).catch((err) => {
                })
        }, createAccount() {
            axios.post("/api/clients/current/accounts")
                .then((response) => {
                    console.log(response)
                }).catch((err) => console.log(err))
        }
    },
}).mount('#app-login');