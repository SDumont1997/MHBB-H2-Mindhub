const app = Vue.createApp({
    data(){
        return {
            client: {},
            accounts: [],
            loans: [],
            addAccountError: "",
            originAccount: "",
            destinationAccount: "",
            transactionAmount: 0.00,
            transactionDetail: "",
            transactionError: "",
            ownAccountsSelector: "activeRadioColor",
            anotherReceiverSelector: "",
            slideLeftOn: "",
            slideRightOn: "slideFromRight",
            frequents: []
        }
    },
    created(){
        this.loadData()

    },
    methods: {
        loadData(){
            axios.get("/api/clients/current")
            .then(response => {
                this.client = response.data
                this.accounts = this.sortById(response.data.accounts)
                this.loans = this.sortById(response.data.loans)
                this.frequents = this.sortById(response.data.frequents)
            })
            .catch(e => {
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
        addAccount(){
            axios.post("/api/clients/current/accounts")
            .then(response => {
                window.location.reload()
            })
            .catch(error => {
                this.addAccountError = error.response.data
            })
        },
        createTransaction(){
            if(this.transactionAmount === ""){
                this.transactionAmount = 0.00
            }
            axios.post("/api/transactions", `originAccountNumber=${this.originAccount}&destinationAccountNumber=${this.destinationAccount}&amount=${this.transactionAmount}&detail=${this.transactionDetail}`)
            .then(response => {
                window.location.reload()
            })
            .catch(error => {
                this.transactionError = error.response.data
            })
        },
        changeAccountSlide(){
            if(this.ownAccountsSelector === "activeRadioColor"){
                this.ownAccountsSelector = ""
                this.anotherReceiverSelector = "activeRadioColor"
                this.slideLeftOn = "slideFromLeft"
                setTimeout(() => {this.slideRightOn = ""}, 250) 
            } else {
                this.ownAccountsSelector = "activeRadioColor"
                this.anotherReceiverSelector = ""
                this.slideRightOn = "slideFromRight"
                setTimeout(() => {this.slideLeftOn = ""}, 250)
            }
            this.originAccount = ""
            this.destinationAccount = ""
            this.transactionAmount = 0.00
            this.transactionDetail = ""
            this.transactionError = ""
        },
        changeDestination(event){
            this.destinationAccount = event.target.value
        }
    },
    computed: {
        maxAccountsReached(){
            if(this.accounts.length >= 3){
            return "ms-lg-0"
            }
        },
        possibleDestinations(){
            return this.accounts.filter(account => account.number !== this.originAccount)
        }
    }
})
app.mount("#app")