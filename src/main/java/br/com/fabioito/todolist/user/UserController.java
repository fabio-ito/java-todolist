package br.com.fabioito.todolist.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.favre.lib.crypto.bcrypt.BCrypt;

// Modificador
// public
// private - restrição de acesso
// protected
@RestController
@RequestMapping("/users")
public class UserController {
  
  // String (texto)
  // Integer (int) números inteiros
  // Double números com vírgulas
  // Float números com vírgulas
  // Char caracteres
  // Date (datas)
  // void (sem nenhum retorno)

  //Body
  @Autowired
  private IUserRepository userRepository;
  @PostMapping("/")
  public ResponseEntity create(@RequestBody UserModel userModel) {
    var user = this.userRepository.findByUsername(userModel.getUsername());

    if(user != null){
      System.out.println("Usuário já existe");
      //Mensagem de erro
      //Status code
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário já existe");
    }

    var passwordHashed = BCrypt.withDefaults().hashToString(12, userModel.getPassword().toCharArray());

    userModel.setPassword(passwordHashed);

    var userCreated = this.userRepository.save(userModel);
    return ResponseEntity.status(HttpStatus.OK).body(userCreated);
  }
}
