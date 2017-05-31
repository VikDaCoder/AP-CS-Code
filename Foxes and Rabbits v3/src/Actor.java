import java.util.List;

public interface Actor {
	 void act(List<Actor> newActors); //could return List instead of having param
	 void setLocation(Location newLocation);                      
	 boolean isAlive();               
} 
