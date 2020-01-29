package com.github.mkopylec.projectmanager.core.team;

import com.github.mkopylec.projectmanager.core.NewTeam;
import com.github.mkopylec.projectmanager.core.NewTeamMember;
import com.github.mkopylec.projectmanager.core.UpdatedProject;
import com.github.mkopylec.projectmanager.core.project.Project;

import java.util.List;
import java.util.function.Consumer;

import static com.github.mkopylec.projectmanager.core.common.Utilities.isEmpty;
import static com.github.mkopylec.projectmanager.core.team.TeamRequirementsValidator.requirements;

public class TeamService {

    private TeamFactory factory = new TeamFactory();
    private TeamRepository repository;

    public TeamService(TeamRepository repository) {
        this.repository = repository;
    }

    public void createTeam(NewTeam newTeam) {
        Team existingTeam = repository.findByName(newTeam.getName());
        requirements()
                .requireNoTeam(existingTeam, "Error creating team named '" + newTeam.getName() + "'")
                .validate();
        Team team = factory.createTeam(newTeam);
        repository.save(team);
    }

    public void addMemberToTeam(String teamName, NewTeamMember newTeamMember) {
        Team team = repository.findByName(teamName);
        requirements()
                .requireTeam(team, "Error adding member to '" + teamName + "' team")
                .validate();
        Employee member = factory.createMember(newTeamMember);
        team.addMember(member);
        repository.save(team);
    }

    public List<Team> getTeams() {
        return repository.findAll();
    }

    public void addImplementedProjectToTeam(Project project) {
        updateTeamAssignedToProject(project, Team::addCurrentlyImplementedProject);
    }

    public void removeImplementedProjectFromTeam(Project project) {
        updateTeamAssignedToProject(project, Team::removeCurrentlyImplementedProject);
    }

    public void ensureTeamAssignedToProjectExists(String projectIdentifier, UpdatedProject updatedProject) {
        if (isEmpty(updatedProject.getTeam())) {
            return;
        }
        Team team = repository.findByName(updatedProject.getTeam());
        requirements()
                .requireTeamAssignedToProject(team, "Error updating '" + projectIdentifier + "' project")
                .validate();
    }

    private void updateTeamAssignedToProject(Project project, Consumer<Team> update) {
        if (project.hasNoTeamAssigned()) {
            return;
        }
        Team team = repository.findByName(project.getAssignedTeam());
        update.accept(team);
        repository.save(team);
    }
}
