const app = Vue.createApp({
    data(){
        return {
            client: {},
            accounts: [],
            loans: [],
            possiblePayments: [],
            loanType: "",
            loanName: "",
            loanPayments: "",
            loanAmount: 0.0,
            destinationAccount: "",
            loanApplicationError: ""
        }
    },
    created(){
        this.loadClientData()
        this.loadLoansData()
    },
    methods: {
        loadClientData(){
            axios.get("/api/clients/current")
            .then(response => {
                this.client = response.data
                this.accounts = this.sortById(response.data.accounts)
            })
            .catch(e => {
                console.log(e)
            })
        },
        loadLoansData(){
            axios.get("/api/loans")
            .then(response => {
                this.loans = this.sortById(response.data)
            }).catch(e => {
                console.log(e)
            })
        },
        sortById(array){
            array.sort((elementA, elementB) => {
                if(elementA.id < elementB.id){
                    return -1
                }
                if(elementA.id > elementB.id){
                    return 1
                }
                return 0
            })
            return array
        },
        logOut(){
            axios.post("/api/logout")
            .then(response => {
                window.location.replace("/web/index.html")
            })
            .catch(error => {
                console.error(error)
            })
        },
        setPayments(){
            this.possiblePayments = this.loans.filter(loan => loan.id === this.loanType)[0].payments
            this.loanName = this.loans.filter(loan => loan.id === this.loanType)[0].name
        },
        createLoanApplication(){
            axios.post("/api/loans", {loanId: this.loanType, loanAmount: this.loanAmount, payments: this.loanPayments, destinationAccountNumber: this.destinationAccount})
            .then(response => {
                window.location.replace("/web/accounts.html")
            })
            .catch(error => {
                this.loanApplicationError = error.response.data
            })
        }
    },
    computed: {

    }
})
app.mount("#app")