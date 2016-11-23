var React = require('react-native');
var Actions = require('../Actions');

var {
  StyleSheet,
  TextInput,
  View,
  TouchableHighlight,
  Text
} = React;

var styles = StyleSheet.create({
 row: {
    flexDirection: 'row',
    margin: 30
  },
  input:{
    flex:8,
    borderWidth:1,
    borderColor:'black'
  },
  button:{
    flex:1,
    padding:3,
    borderWidth:1,
    marginLeft:10,
    borderColor:'black',
    borderRadius:5
  }
});

module.exports = class extends React.Component{

  constructor(props) {
      super(props);
      this.state = {
        todoText:""
      };

      this.onSavePress = this._onSavePress.bind(this);
      this.onClearPress = this._onClearPress.bind(this);
      this.onChangeInput = this._onChangeInput.bind(this);
  }

  _onSavePress(){
    if(this.state.todoText){
      Actions.add(this.state.todoText);
      this.setState({
        todoText:""
      });
    }
  }
  _onClearPress(){
      Actions.clear();
  }

  _onChangeInput(event){
    this.setState({
      todoText:event.nativeEvent.text
    });
  }

	render() {
	    return (
        <View style={styles.row}>
	    	  <TextInput
            placeholder="添加任务"
            onChange={this.onChangeInput}
            style={styles.input}
            value={this.state.todoText}/>
          <TouchableHighlight
            onPress={this.onSavePress}
            style={styles.button}>
              <Text style={{textAlign:'center'}}>+</Text>
          </TouchableHighlight>
          <TouchableHighlight
            onPress={this.onClearPress}
            style={styles.button}>
              <Text style={{textAlign:'center'}}>-</Text>
          </TouchableHighlight>
        </View>
	    );
	}
}
