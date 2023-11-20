let { createApp } = Vue;

createApp({
    data() {
        return {
            loansAvailable: [],
            accountsOwn: [],
            TypeLoan: "",
            amount: "",
            accountTransfer: "",
            payments: "",
            loansAvailableCoutas: [],
            calculateQuota: "",
            advertencia: "",
        };
    },
    created() {

    },
    methods: {
        loans() {
            axios.get("/api/loans")
                .then((response) => {
                    this.loansAvailable = response.data

                    console.log(this.loansAvailable)
                }).catch((err) => console.log(err))
        },

        accounts() {
            axios.get("/api/clients/current/accounts")
                .then((response) => {
                    this.accountsOwn = response.data.filter(accounts => accounts.active == true).map(account => account.number)
                }).catch((err) => console.log(err))
        },

        newloan() {
            axios.post("/api/loans", {
                id: this.TypeLoan.id,
                amount: this.amount,
                payments: this.payments,
                numberAccount: this.accountTransfer,
            }
            ).then((response) => {
                Swal.fire({
                    position: 'center',
                    icon: 'success',
                    iconColor: 'grey',
                    title: 'Approved loan',
                    showConfirmButton: false,
                    timer: 900
                }), setTimeout(() => {
                    window.location.href = "./accounts.html";
                }, 1700)

            }).catch((err) => {
                let mesageerrorloan = err.response ? err.response.data : 'Error desconocido';
                Swal.fire({
                    position: 'center',
                    icon: 'error',
                    iconColor: 'red',
                    text: mesageerrorloan,
                    showConfirmButton: false,
                    timer: 1500,
                    customClass: {
                        text: 'custom-swal-text' // Definir una clase personalizada
                    }
                })
            })
        },

    },
    computed: {
        metodo() {
            console.log(this.TypeLoan)
            console.log(this.TypeLoan.id, this.amount, this.payments, this.accountTransfer)
        },
        typeloan() {
            this.loansAvailableCoutas = this.TypeLoan.payments
        },
        capturarValorCuota() {
            if (this.amount && this.payments) {
                let valorAmount = parseFloat(this.amount)
                this.calculateQuota = "Monthly payment will be $" + ((valorAmount + (valorAmount * this.TypeLoan.interes / 100)) / this.payments).toFixed(2)
                    + " and interes will be " + this.TypeLoan.interes + "%"
            } else {
                this.calculateQuota = ""
            }
        },
        maxAmount() {
            if (this.amount > this.TypeLoan.maxAmount) {
                this.advertencia = `The maximum amount available to request is $${this.TypeLoan.maxAmount.toLocaleString()}`

                console.log(this.advertencia)
            } else {
                this.advertencia = ""
            }
        },


    }
}).mount('#app');


