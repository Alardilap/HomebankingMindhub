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

                }).catch((err) => console.log(err))

        },
        accounts() {
            axios.get("/api/clients/current/accounts")
                .then((response) => {
                    this.accountsOwn = response.data.map(account => account.number)
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
                console.log(response.data)

            }).catch((err) => console.log(err))
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
                this.calculateQuota = "Monthly payment will be $" + ((valorAmount + (valorAmount * 0.20)) / this.payments).toFixed(2);
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

        }
    }
}).mount('#app');


