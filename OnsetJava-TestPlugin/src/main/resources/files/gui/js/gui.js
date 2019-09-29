class GUI {
    constructor(){
        this.data = {};
        this.ondata = (data) => {}
    }
    setData(data){
        this.data = data;
        this.ondata(data);
    }
    callEvent(name, ...args){
		if (typeof name != "string") {
			return;
		}
		if (args.length == 0) {
			ue.game.callevent(name, "")
		} else {
			let params = []
			for (let i = 0; i < args.length; i++) {
				params[i] = args[i];
			}
			ue.game.callevent(name, JSON.stringify(params));
		}
	}
}
gui = new GUI();