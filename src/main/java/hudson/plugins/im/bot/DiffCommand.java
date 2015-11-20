package hudson.plugins.im.bot;

import java.util.Collection;
import java.util.Collections;

import hudson.Extension;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;

/**
 * Returns a list of changed files
 * @author pzhao12 admathu2
 */
@Extension
public class DiffCommand extends AbstractMultipleJobCommand {
	@Override
    public Collection<String> getCommandNames() {
        return Collections.singleton("diff");
    }

    private static final String HELP = " diff <job> ";

    @Override
    protected CharSequence getMessageForJob(AbstractProject<?, ?> project) {
        
    	StringBuilder msg = new StringBuilder(32);
        msg.append(project.getFullDisplayName());
        

        if (project.isDisabled()) {
            msg.append("(disabled)");
        } else if (project.isBuilding()) {
            msg.append("(BUILDING: ").append(project.getLastBuild().getDurationString()).append(")");
        } else if (project.isInQueue()) {
            msg.append("(in queue)");
        }
        msg.append(": ");

        AbstractBuild<?, ?> lastBuild = project.getLastBuild();
        while ((lastBuild != null) && lastBuild.isBuilding()) {
            lastBuild = lastBuild.getPreviousBuild();
        }
        
        //TODO: Implement Diff command
        if (lastBuild != null) {
        	
        	msg.append("Diff command has not been implemented yet. Sorry!");
          } 
    	else {
            msg.append("no finished build yet");
        }
        return msg;
    }
    
    

    @Override
    protected String getCommandShortName() {
        return "diff";
    }
	public String getHelp() {
		return HELP;
	}

}